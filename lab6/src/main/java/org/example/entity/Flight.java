package org.example.entity;
import java.util.Objects;
public abstract class Flight {
    private final String flightNumber;
    private final String destination;
    private final String vesselBrand;
    private AircraftStatus status;
    private final double equippedMassKg;
    private double fuelLiters;
    private double averageFlightTimeHours;
    protected Flight(String flightNumber, String destination, String vesselBrand,
                     AircraftStatus status, double equippedMassKg,
                     double fuelLiters, double averageFlightTimeHours) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.vesselBrand = vesselBrand;
        this.status = status;
        this.equippedMassKg = equippedMassKg;
        this.fuelLiters = fuelLiters;
        this.averageFlightTimeHours = averageFlightTimeHours;
    }
    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
    public String getVesselBrand() { return vesselBrand; }
    public AircraftStatus getStatus() { return status; }
    public double getEquippedMassKg() { return equippedMassKg; }
    public double getFuelLiters() { return fuelLiters; }
    public double getAverageFlightTimeHours() { return averageFlightTimeHours; }
    public void setStatus(AircraftStatus status) { this.status = status; }
    public void setFuelLiters(double fuelLiters) { this.fuelLiters = fuelLiters; }
    public void setAverageFlightTimeHours(double v) { this.averageFlightTimeHours = v; }
    public abstract String getFlightType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }

    @Override
    public String toString() {
        return getFlightType() + "{number='" + flightNumber + "', destination='" + destination
                + "', vessel='" + vesselBrand + "', status=" + status
                + ", mass=" + equippedMassKg + ", fuel=" + fuelLiters
                + ", avgTime=" + averageFlightTimeHours + '}';
    }
}