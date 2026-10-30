package org.example.parser;
import org.example.entity.Flight;
import org.example.exception.InvalidFlightDataException;
import org.example.factory.FlightFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class FlightFileParser {
    private static final Logger LOG = LogManager.getLogger(FlightFileParser.class);
    private final FlightFactory factory;
    public FlightFileParser(FlightFactory factory) {
        this.factory = factory;
    }
    public List<Flight> parseFile(String filePath) throws IOException {
        List<Flight> flights = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filePath));
        int lineNumber = 0;
        for (String raw : lines) {
            lineNumber++;
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            try {
                String[] parts = line.split(";");
                Flight flight = factory.createFlight(parts);
                flights.add(flight);
            } catch (InvalidFlightDataException e) {
                LOG.error("Line {} skipped: {}", lineNumber, e.getMessage());
            } catch (RuntimeException e) {
                LOG.error("Line {} failed unexpectedly: {}", lineNumber, e.getMessage());
            }
        }
        LOG.info("Parsed {} flights from {}", flights.size(), filePath);
        return flights;
    }
}