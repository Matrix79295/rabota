package org.example.factory;
import org.example.entity.Flight;
import org.example.exception.InvalidFlightDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
public class FlightFactory {
    private static final Logger LOG = LogManager.getLogger(FlightFactory.class);
    private final Map<String, FlightCreator> creators = new HashMap<>();

    public FlightFactory() {
        register(new CargoFlightCreator());
        register(new PassengerFlightCreator());
    }

    public void register(FlightCreator creator) {
        creators.put(creator.getSupportedType(), creator);
    }

    public Flight createFlight(String[] data) throws InvalidFlightDataException {
        if (data == null || data.length < 9) {
            throw new InvalidFlightDataException("Not enough fields in data line");
        }
        FlightCreator creator = creators.get(data[0].toUpperCase());
        if (creator == null) {
            throw new InvalidFlightDataException("Unknown flight type: " + data[0]);
        }
        Flight flight = creator.createFlight(data);
        LOG.info("Factory created flight {}", flight.getFlightNumber());
        return flight;
    }
}