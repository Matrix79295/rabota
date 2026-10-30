package org.example.factory;
import org.example.entity.AircraftStatus;
import org.example.entity.Flight;
import org.example.entity.PassengerFlight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class PassengerFlightCreator extends FlightCreator {
    private static final Logger LOG = LogManager.getLogger(PassengerFlightCreator.class);

    @Override
    public String getSupportedType() { return "PASSENGER"; }

    @Override
    public Flight createFlight(String[] d) {
        String number = d[1];
        String destination = d[2];
        String vessel = d[3];
        AircraftStatus status = parseStatus(d[4]);
        double mass = parseDouble(d[5], 0);
        double fuel = parseDouble(d[6], 0);
        double avg = parseDouble(d[7], 0);
        int capacity = (int) parseDouble(d[8], 0);
        boolean business = d.length > 9 && Boolean.parseBoolean(d[9]);
        PassengerFlight flight = new PassengerFlight(number, destination, vessel, status,
                mass, fuel, avg, capacity, business);
        LOG.debug("Created passenger flight {}", number);
        return flight;
    }

    private AircraftStatus parseStatus(String s) {
        try {
            return AircraftStatus.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.warn("Invalid status '{}' for passenger flight, defaulting to READY_FOR_DEPARTURE", s);
            return AircraftStatus.READY_FOR_DEPARTURE;
        }
    }

    private double parseDouble(String s, double def) {
        try {
            double v = Double.parseDouble(s);
            if (v < 0) throw new NumberFormatException("negative");
            return v;
        } catch (NumberFormatException | NullPointerException e) {
            LOG.warn("Invalid numeric value '{}', defaulting to {}", s, def);
            return def;
        }
    }
}