package org.example.factory;
import org.example.entity.Flight;
public abstract class FlightCreator {
    public abstract Flight createFlight(String[] data);
    public abstract String getSupportedType();
}