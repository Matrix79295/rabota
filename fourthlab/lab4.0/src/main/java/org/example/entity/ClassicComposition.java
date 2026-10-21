package org.example.entity;
public class ClassicComposition extends Composition {
    private final String composer;
    public ClassicComposition(String title, String artist, int durationSeconds, String composer) {
        super(title, artist, durationSeconds, "Classic");
        this.composer = composer;
    }
    public String getComposer() { return composer; }
}