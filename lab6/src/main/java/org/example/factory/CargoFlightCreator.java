package org.example.factory;
import org.example.entity.AircraftStatus;
import org.example.entity.Cargo;
import org.example.entity.CargoFlight;
import org.example.entity.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class CargoFlightCreator extends FlightCreator {
    private static final Logger LOG = LogManager.getLogger(CargoFlightCreator.class);

    @Override
    public String getSupportedType() { return "CARGO"; }

    @Override
    public Flight createFlight(String[] d) {
        String number = d[1];
        String destination = d[2];
        String vessel = d[3];
        AircraftStatus status = parseStatus(d[4]);
        double mass = parseDouble(d[5], 0);
        double fuel = parseDouble(d[6], 0);
        double avg = parseDouble(d[7], 0);
        double maxPayload = parseDouble(d[8], 0);

        CargoFlight flight = new CargoFlight(number, destination, vessel, status,
                mass, fuel, avg, maxPayload);

        if (d.length >= 12) {
            Cargo cargo = new Cargo(
                    d[9].isEmpty() ? "Unknown" : d[9],
                    parseDouble(d[10], 0),
                    Boolean.parseBoolean(d[11]),
                    d.length > 12 && Boolean.parseBoolean(d[12])
            );
            flight.addCargo(cargo);
        }
        LOG.debug("Created cargo flight {}", number);
        return flight;
    }

    private AircraftStatus parseStatus(String s) {
        try {
            return AircraftStatus.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.warn("Invalid status '{}' for cargo flight, defaulting to READY_FOR_DEPARTURE", s);
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