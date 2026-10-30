package org.example.comparator;
import org.example.entity.Flight;
import java.util.Comparator;
public class FlightNumberComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight a, Flight b) {
        return a.getFlightNumber().compareToIgnoreCase(b.getFlightNumber());
    }
}