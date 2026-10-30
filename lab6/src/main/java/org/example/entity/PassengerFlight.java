package org.example.entity;
public class PassengerFlight extends Flight {
    private int passengerCapacity;
    private int currentPassengers;
    private boolean businessClassAvailable;
    public PassengerFlight(String flightNumber, String destination, String vesselBrand,
                           AircraftStatus status, double equippedMassKg, double fuelLiters,
                           double averageFlightTimeHours, int passengerCapacity,
                           boolean businessClassAvailable) {
        super(flightNumber, destination, vesselBrand, status, equippedMassKg,
                fuelLiters, averageFlightTimeHours);
        this.passengerCapacity = passengerCapacity;
        this.businessClassAvailable = businessClassAvailable;
    }
    public int getPassengerCapacity() { return passengerCapacity; }
    public void setPassengerCapacity(int v) { this.passengerCapacity = v; }
    public int getCurrentPassengers() { return currentPassengers; }
    public void setCurrentPassengers(int v) { this.currentPassengers = v; }
    public boolean isBusinessClassAvailable() { return businessClassAvailable; }
    public void setBusinessClassAvailable(boolean v) { this.businessClassAvailable = v; }
    public boolean isFull() { return currentPassengers >= passengerCapacity; }

    @Override
    public String getFlightType() { return "PassengerFlight"; }

    @Override
    public String toString() {
        return super.toString() + " | capacity=" + passengerCapacity
                + ", passengers=" + currentPassengers
                + ", business=" + businessClassAvailable;
    }
}