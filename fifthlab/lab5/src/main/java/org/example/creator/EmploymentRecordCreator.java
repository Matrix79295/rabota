package org.example.creator;
import org.example.entity.Employee;
import org.example.entity.EmploymentRecord;
import org.example.exception.InvalidDataException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class EmploymentRecordCreator extends RecordCreator<EmploymentRecord> {
    @Override
    public EmploymentRecord create(String[] data) throws InvalidDataException {
        if (data == null || data.length < 3)
            throw new InvalidDataException("Ожидалось: имя;возраст;датаНачала;[датаКонца]");
        Employee emp = new EmployeeCreator().create(new String[]{data[0], data[1]});
        LocalDate start;
        try {
            start = LocalDate.parse(data[2].trim());
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Некорректная дата начала: " + data[2], e);
        }
        LocalDate end = null;
        if (data.length > 3 && !data[3].trim().isEmpty()) {
            try {
                end = LocalDate.parse(data[3].trim());
            } catch (DateTimeParseException e) {
                throw new InvalidDataException("Некорректная дата конца: " + data[3], e);
            }
            if (end.isBefore(start))
                throw new InvalidDataException("Дата конца раньше даты начала");
        }
        return new EmploymentRecord(emp, start, end);
    }
}