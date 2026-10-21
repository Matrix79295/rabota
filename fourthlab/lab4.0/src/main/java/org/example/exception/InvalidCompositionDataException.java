package org.example.exception;
public class InvalidCompositionDataException extends Exception {
    public InvalidCompositionDataException(String message) {
        super(message);
    }
    public InvalidCompositionDataException(String message, Throwable cause) {
        super(message, cause);
    }
}