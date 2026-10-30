package org.example;
import org.example.comparator.FlightNumberComparator;
import org.example.comparator.FlightStatusComparator;
import org.example.entity.*;
import org.example.exception.InvalidFlightDataException;
import org.example.factory.FlightFactory;
import org.example.parser.FlightFileParser;
import org.example.service.FlightService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class FlightTestSuite {

    private FlightFactory factory;

    @BeforeEach
    void setUp() {
        factory = new FlightFactory();
    }

    @Test
    void factoryCreatesCargoFlight() throws Exception {
        String[] data = {"CARGO", "CF100", "Berlin", "Boeing 747F", "LOADING",
                "80000", "15000", "4.5", "120000", "Electronics", "500", "true", "true"};
        Flight f = factory.createFlight(data);
        assertInstanceOf(CargoFlight.class, f);
        CargoFlight cf = (CargoFlight) f;
        assertEquals("CF100", cf.getFlightNumber());
        assertEquals(AircraftStatus.LOADING, cf.getStatus());
        assertTrue(cf.hasFragileCargo());
        assertTrue(cf.hasValuableCargo());
    }

    @Test
    void factoryCreatesPassengerFlight() throws Exception {
        String[] data = {"PASSENGER", "PF200", "Paris", "Airbus A320", "READY_FOR_DEPARTURE",
                "40000", "8000", "3.0", "180", "true"};
        Flight f = factory.createFlight(data);
        assertInstanceOf(PassengerFlight.class, f);
        PassengerFlight pf = (PassengerFlight) f;
        assertEquals(180, pf.getPassengerCapacity());
        assertTrue(pf.isBusinessClassAvailable());
    }

    @Test
    void factoryThrowsOnUnknownType() {
        String[] data = {"UNKNOWN", "X", "Y", "Z", "LOADING", "1", "1", "1", "1"};
        assertThrows(InvalidFlightDataException.class, () -> factory.createFlight(data));
    }

    @Test
    void factoryThrowsOnShortData() {
        String[] data = {"CARGO", "CF"};
        assertThrows(InvalidFlightDataException.class, () -> factory.createFlight(data));
    }

    @Test
    void factoryReplacesInvalidStatusWithDefault() throws Exception {
        String[] data = {"PASSENGER", "PF201", "Rome", "Boeing", "BROKEN_STATUS",
                "40000", "8000", "3.0", "180", "false"};
        Flight f = factory.createFlight(data);
        assertEquals(AircraftStatus.READY_FOR_DEPARTURE, f.getStatus());
    }

    @Test
    void factoryReplacesInvalidNumberWithDefault() throws Exception {
        String[] data = {"PASSENGER", "PF202", "Rome", "Boeing", "LOADING",
                "not-a-number", "8000", "3.0", "180", "false"};
        Flight f = factory.createFlight(data);
        assertEquals(0.0, f.getEquippedMassKg());
        assertEquals(8000.0, f.getFuelLiters());
    }

    @Test
    void parserReadsValidFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("flights.txt");
        Files.writeString(file, String.join("\n",
                "PASSENGER;PF1;Paris;Airbus;READY_FOR_DEPARTURE;40000;8000;3.0;180;true",
                "CARGO;CF1;Berlin;Boeing;LOADING;80000;15000;4.5;120000;Box;500;false;true",
                "# comment line",
                "",
                "BROKEN;X;Y;Z;LOADING;1;1;1;1"
        ));
        FlightFileParser parser = new FlightFileParser(factory);
        List<Flight> flights = parser.parseFile(file.toString());
        assertEquals(2, flights.size());
    }

    @Test
    void parserHandlesMissingFile() {
        FlightFileParser parser = new FlightFileParser(factory);
        assertThrows(IOException.class, () -> parser.parseFile("/no/such/file.txt"));
    }

    private FlightService buildService() throws Exception {
        String[] a = {"PASSENGER", "PF1", "Paris", "Airbus", "READY_FOR_DEPARTURE",
                "40000", "8000", "3.0", "180", "true"};
        String[] b = {"CARGO", "CF1", "Berlin", "Boeing", "LOADING",
                "80000", "15000", "4.5", "120000", "Box", "500", "true", "true"};
        Flight f1 = factory.createFlight(a);
        Flight f2 = factory.createFlight(b);
        return new FlightService(List.of(f1, f2));
    }

    @Test
    void serviceFindsFlightByNumber() throws Exception {
        FlightService service = buildService();
        assertTrue(service.findByNumber("pf1").isPresent());
        assertTrue(service.findByNumber("MISSING").isEmpty());
    }

    @Test
    void serviceSendsToDestination() throws Exception {
        FlightService service = buildService();
        service.sendToDestination("PF1");
        assertEquals(AircraftStatus.IN_FLIGHT,
                service.findByNumber("PF1").orElseThrow().getStatus());
    }

    @Test
    void serviceRepairsFlight() throws Exception {
        FlightService service = buildService();
        service.repair("CF1");
        assertEquals(AircraftStatus.READY_FOR_DEPARTURE,
                service.findByNumber("CF1").orElseThrow().getStatus());
    }

    @Test
    void serviceRefuelsFlight() throws Exception {
        FlightService service = buildService();
        double before = service.findByNumber("PF1").orElseThrow().getFuelLiters();
        service.refuel("PF1", 500);
        assertEquals(before + 500,
                service.findByNumber("PF1").orElseThrow().getFuelLiters());
        assertEquals(AircraftStatus.REFUELING,
                service.findByNumber("PF1").orElseThrow().getStatus());
    }

    @Test
    void serviceAverageFlightTime() throws Exception {
        FlightService service = buildService();
        assertEquals(3.75, service.getAverageFlightTime(), 0.0001);
    }

    @Test
    void serviceTotalFuel() throws Exception {
        FlightService service = buildService();
        assertEquals(23000.0, service.getTotalFuel(), 0.0001);
    }

    @Test
    void serviceCargoFlags() throws Exception {
        FlightService service = buildService();
        assertTrue(service.isCargoFragile("CF1"));
        assertTrue(service.isCargoValuable("CF1"));
        assertFalse(service.isCargoFragile("PF1"));
    }

    @Test
    void serviceLoadAndUnload() throws Exception {
        FlightService service = buildService();
        service.load("PF1");
        assertEquals(AircraftStatus.LOADING,
                service.findByNumber("PF1").orElseThrow().getStatus());
        service.unload("PF1");
        assertEquals(AircraftStatus.UNLOADING,
                service.findByNumber("PF1").orElseThrow().getStatus());
    }

    @Test
    void comparatorSortsByNumber() throws Exception {
        FlightService service = buildService();
        List<Flight> sorted = service.sortedByNumber();
        assertEquals("CF1", sorted.get(0).getFlightNumber());
        assertEquals("PF1", sorted.get(1).getFlightNumber());
        assertDoesNotThrow(() -> new FlightNumberComparator().compare(sorted.get(0), sorted.get(1)));
    }

    @Test
    void comparatorSortsByStatus() throws Exception {
        FlightService service = buildService();
        List<Flight> sorted = service.sortedByStatus();
        assertTrue(new FlightStatusComparator().compare(sorted.get(0), sorted.get(1)) <= 0);
    }

    @Test
    void cargoFlightAggregatesWeight() {
        CargoFlight cf = new CargoFlight("CF9", "Moscow", "IL-76",
                AircraftStatus.LOADING, 90000, 20000, 5.0, 100000);
        cf.addCargo(new Cargo("A", 100, false, false));
        cf.addCargo(new Cargo("B", 250.5, true, true));
        assertEquals(350.5, cf.getCurrentCargoWeight(), 0.0001);
        assertTrue(cf.hasFragileCargo());
        assertTrue(cf.hasValuableCargo());
    }

    @Test
    void passengerFlightFullCheck() {
        PassengerFlight pf = new PassengerFlight("PF9", "Sochi", "SSJ-100",
                AircraftStatus.LOADING, 30000, 5000, 2.0, 100, true);
        assertFalse(pf.isFull());
        pf.setCurrentPassengers(100);
        assertTrue(pf.isFull());
    }

    @Test
    void flightEqualityByNumber() {
        CargoFlight c = new CargoFlight("N1", "A", "B", AircraftStatus.LOADING,
                1, 1, 1, 1);
        PassengerFlight p = new PassengerFlight("N1", "A", "B", AircraftStatus.IN_FLIGHT,
                1, 1, 1, 1, false);
        assertEquals(c, p);
        assertEquals(c.hashCode(), p.hashCode());
    }
}