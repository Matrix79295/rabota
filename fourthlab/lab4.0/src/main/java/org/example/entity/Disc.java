package org.example.entity;
import java.util.ArrayList;
import java.util.List;
public class Disc {
    private final List<Composition> compositions = new ArrayList<>();
    public void addComposition(Composition c) {
        compositions.add(c);
    }
    public List<Composition> getCompositions() {
        return new ArrayList<>(compositions);
    }
    public void setCompositions(List<Composition> newCompositions) {
        compositions.clear();
        compositions.addAll(newCompositions);
    }
    public int getTotalDuration() {
        return compositions.stream().mapToInt(Composition::getDurationSeconds).sum();
    }
}