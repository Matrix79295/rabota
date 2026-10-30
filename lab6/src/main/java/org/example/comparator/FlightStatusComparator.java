package org.example.comparator;
import org.example.entity.Flight;
import java.util.Comparator;
public class FlightStatusComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight a, Flight b) {
        return a.getStatus().compareTo(b.getStatus());
    }
}