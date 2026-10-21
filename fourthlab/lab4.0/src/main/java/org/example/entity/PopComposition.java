package org.example.entity;
public class PopComposition extends Composition {
    private final int chartPosition;
    public PopComposition(String title, String artist, int durationSeconds, int chartPosition) {
        super(title, artist, durationSeconds, "Pop");
        this.chartPosition = chartPosition;
    }
    public int getChartPosition() { return chartPosition; }
}