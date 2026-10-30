package org.example.exception;
public class InvalidFlightDataException extends Exception {
    public InvalidFlightDataException(String message) {
        super(message);
    }
    public InvalidFlightDataException(String message, Throwable cause) {
        super(message, cause);
    }
}