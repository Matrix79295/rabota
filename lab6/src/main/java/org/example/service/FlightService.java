package org.example.service;
import org.example.comparator.FlightNumberComparator;
import org.example.comparator.FlightStatusComparator;
import org.example.entity.AircraftStatus;
import org.example.entity.CargoFlight;
import org.example.entity.Flight;
import org.example.entity.PassengerFlight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class FlightService {
    private static final Logger LOG = LogManager.getLogger(FlightService.class);
    private final List<Flight> flights;
    public FlightService(List<Flight> flights) {
        this.flights = new ArrayList<>(flights);
    }
    public List<Flight> getFlights() { return flights; }
    public Optional<Flight> findByNumber(String number) {
        return flights.stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(number))
                .findFirst();
    }
    public void sendToDestination(String number) {
        findByNumber(number).ifPresentOrElse(f -> {
            f.setStatus(AircraftStatus.IN_FLIGHT);
            LOG.info("Flight {} sent to {}", number, f.getDestination());
        }, () -> LOG.warn("Flight {} not found for sending", number));
    }
    public void repair(String number) {
        findByNumber(number).ifPresentOrElse(f -> {
            f.setStatus(AircraftStatus.READY_FOR_DEPARTURE);
            LOG.info("Flight {} repaired", number);
        }, () -> LOG.warn("Flight {} not found for repair", number));
    }
    public void refuel(String number, double liters) {
        findByNumber(number).ifPresentOrElse(f -> {
            f.setFuelLiters(f.getFuelLiters() + liters);
            f.setStatus(AircraftStatus.REFUELING);
            LOG.info("Flight {} refueled by {} L, total {}", number, liters, f.getFuelLiters());
        }, () -> LOG.warn("Flight {} not found for refuel", number));
    }
    public void load(String number) {
        findByNumber(number).ifPresentOrElse(f -> {
            f.setStatus(AircraftStatus.LOADING);
            LOG.info("Flight {} is loading", number);
        }, () -> LOG.warn("Flight {} not found for loading", number));
    }
    public void unload(String number) {
        findByNumber(number).ifPresentOrElse(f -> {
            f.setStatus(AircraftStatus.UNLOADING);
            LOG.info("Flight {} is unloading", number);
        }, () -> LOG.warn("Flight {} not found for unloading", number));
    }
    public double getAverageFlightTime() {
        return flights.stream()
                .mapToDouble(Flight::getAverageFlightTimeHours)
                .average().orElse(0);
    }
    public double getTotalFuel() {
        return flights.stream().mapToDouble(Flight::getFuelLiters).sum();
    }
    public boolean isCargoFragile(String number) {
        return findByNumber(number)
                .filter(f -> f instanceof CargoFlight)
                .map(f -> ((CargoFlight) f).hasFragileCargo())
                .orElse(false);
    }
    public boolean isCargoValuable(String number) {
        return findByNumber(number)
                .filter(f -> f instanceof CargoFlight)
                .map(f -> ((CargoFlight) f).hasValuableCargo())
                .orElse(false);
    }
    public List<Flight> sortedByNumber() {
        List<Flight> copy = new ArrayList<>(flights);
        copy.sort(new FlightNumberComparator());
        LOG.debug("Sorted flights by number");
        return copy;
    }
    public List<Flight> sortedByStatus() {
        List<Flight> copy = new ArrayList<>(flights);
        copy.sort(new FlightStatusComparator());
        LOG.debug("Sorted flights by status");
        return copy;
    }
    public long countByType(Class<? extends Flight> type) {
        return flights.stream().filter(type::isInstance).count();
    }
    public List<PassengerFlight> getPassengerFlights() {
        List<PassengerFlight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f instanceof PassengerFlight) result.add((PassengerFlight) f);
        }
        return result;
    }
}