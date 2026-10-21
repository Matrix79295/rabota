package org.example.creator;
import org.example.entity.Employee;
import org.example.exception.InvalidDataException;
public class EmployeeCreator extends RecordCreator<Employee> {

    @Override
    public Employee create(String[] data) throws InvalidDataException {
        if (data == null || data.length < 2)
            throw new InvalidDataException("Ожидалось: имя;возраст");
        String name = data[0].trim();
        if (name.isEmpty())
            throw new InvalidDataException("Пустое имя сотрудника");
        int age;
        try {
            age = Integer.parseInt(data[1].trim());
        } catch (NumberFormatException e) {
            throw new InvalidDataException("Некорректный возраст: " + data[1], e);
        }
        if (age < 16 || age > 100)
            throw new InvalidDataException("Возраст вне допустимого диапазона: " + age);
        return new Employee(name, age);
    }
}