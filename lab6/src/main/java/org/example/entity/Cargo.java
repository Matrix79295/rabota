package org.example.entity;
import java.util.Objects;
public class Cargo {
    private final String name;
    private final double weightKg;
    private final boolean fragile;
    private final boolean valuable;
    public Cargo(String name, double weightKg, boolean fragile, boolean valuable) {
        this.name = name;
        this.weightKg = weightKg;
        this.fragile = fragile;
        this.valuable = valuable;
    }
    public String getName() { return name; }
    public double getWeightKg() { return weightKg; }
    public boolean isFragile() { return fragile; }
    public boolean isValuable() { return valuable; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cargo)) return false;
        Cargo cargo = (Cargo) o;
        return Double.compare(cargo.weightKg, weightKg) == 0
                && fragile == cargo.fragile
                && valuable == cargo.valuable
                && Objects.equals(name, cargo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weightKg, fragile, valuable);
    }

    @Override
    public String toString() {
        return "Cargo{name='" + name + "', weightKg=" + weightKg
                + ", fragile=" + fragile + ", valuable=" + valuable + '}';
    }
}