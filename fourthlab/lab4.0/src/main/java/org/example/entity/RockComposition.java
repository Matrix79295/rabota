package org.example.entity;
public class RockComposition extends Composition {
    private final String subGenre;
    public RockComposition(String title, String artist, int durationSeconds, String subGenre) {
        super(title, artist, durationSeconds, "Rock");
        this.subGenre = subGenre;
    }
    public String getSubGenre() { return subGenre; }
}