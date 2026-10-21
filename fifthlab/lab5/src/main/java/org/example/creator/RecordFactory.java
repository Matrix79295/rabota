package org.example.creator;
import org.example.exception.InvalidDataException;
public class RecordFactory {
    public enum Type { EMPLOYEE, RECORD, POSITION_STR }

    @SuppressWarnings("unchecked")
    public static <T> RecordCreator<T> getCreator(Type type) {
        if (type == Type.EMPLOYEE) {
            return (RecordCreator<T>) new EmployeeCreator();
        } else if (type == Type.RECORD) {
            return (RecordCreator<T>) new EmploymentRecordCreator();
        } else {
            throw new IllegalArgumentException("Not a creator type");
        }
    }
    public static ParsedLine parse(String rawLine) throws InvalidDataException {
        if (rawLine == null || rawLine.isBlank() || rawLine.startsWith("#"))
            throw new InvalidDataException("Пустая строка или комментарий");
        String[] parts = rawLine.split(";");
        if (parts.length < 2)
            throw new InvalidDataException("Мало полей в строке: " + rawLine);
        String type = parts[0].trim().toUpperCase();
        if (type.equals("POSITION")) {
            return new ParsedLine(Type.POSITION_STR, parts);
        } else if (type.equals("RECORD")) {
            return new ParsedLine(Type.RECORD, parts);
        } else {
            throw new InvalidDataException("Неизвестный тип строки: " + type);
        }
    }
    public static class ParsedLine {
        public final Type type;
        public final String[] fields;
        public ParsedLine(Type type, String[] fields) {
            this.type = type;
            this.fields = fields;
        }
    }
}