package org.example.entity;
import java.util.ArrayList;
import java.util.List;
public class CargoFlight extends Flight {
    private final List<Cargo> cargoList = new ArrayList<>();
    private double maxPayloadKg;
    public CargoFlight(String flightNumber, String destination, String vesselBrand,
                       AircraftStatus status, double equippedMassKg, double fuelLiters,
                       double averageFlightTimeHours, double maxPayloadKg) {
        super(flightNumber, destination, vesselBrand, status, equippedMassKg,
                fuelLiters, averageFlightTimeHours);
        this.maxPayloadKg = maxPayloadKg;
    }
    public void addCargo(Cargo cargo) { cargoList.add(cargo); }
    public List<Cargo> getCargoList() { return cargoList; }
    public double getMaxPayloadKg() { return maxPayloadKg; }
    public void setMaxPayloadKg(double maxPayloadKg) { this.maxPayloadKg = maxPayloadKg; }
    public double getCurrentCargoWeight() {
        return cargoList.stream().mapToDouble(Cargo::getWeightKg).sum();
    }
    public boolean hasFragileCargo() {
        return cargoList.stream().anyMatch(Cargo::isFragile);
    }
    public boolean hasValuableCargo() {
        return cargoList.stream().anyMatch(Cargo::isValuable);
    }

    @Override
    public String getFlightType() { return "CargoFlight"; }

    @Override
    public String toString() {
        return super.toString() + " | cargoCount=" + cargoList.size()
                + ", fragile=" + hasFragileCargo() + ", valuable=" + hasValuableCargo();
    }
}