package org.example.comparator;
import org.example.entity.Composition;
import java.util.Comparator;
public class DurationComparator implements Comparator<Composition> {

    @Override
    public int compare(Composition o1, Composition o2) {
        return Integer.compare(o1.getDurationSeconds(), o2.getDurationSeconds());
    }
}