package org.example.creator;
import org.example.exception.InvalidDataException;
public abstract class RecordCreator<T> {
    public abstract T create(String[] data) throws InvalidDataException;
}