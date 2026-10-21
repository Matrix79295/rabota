package org.example.comparator;
import org.example.entity.Composition;
import java.util.Comparator;
public class StyleComparator implements Comparator<Composition> {

    @Override
    public int compare(Composition o1, Composition o2) {
        return o1.getStyle().compareToIgnoreCase(o2.getStyle());
    }
}