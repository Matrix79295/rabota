package org.example.entity;
import java.util.Objects;
public abstract class Composition {
    private final String title;
    private final String artist;
    private final int durationSeconds;
    private final String style;
    protected Composition(String title, String artist, int durationSeconds, String style) {
        this.title = title;
        this.artist = artist;
        this.durationSeconds = durationSeconds;
        this.style = style;
    }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public int getDurationSeconds() { return durationSeconds; }
    public String getStyle() { return style; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Composition)) return false;
        Composition that = (Composition) o;
        return durationSeconds == that.durationSeconds
                && Objects.equals(title, that.title)
                && Objects.equals(artist, that.artist)
                && Objects.equals(style, that.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, durationSeconds, style);
    }

    @Override
    public String toString() {
        return String.format("%s - %s [%s] %d сек.", artist, title, style, durationSeconds);
    }
}