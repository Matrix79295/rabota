package org.example.entity;
import java.util.Objects;
public class Employee {
    private final String fullName;
    private final int age;
    public Employee(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee e = (Employee) o;
        return age == e.age && Objects.equals(fullName, e.fullName);
    }

    @Override
    public int hashCode() { return Objects.hash(fullName, age); }

    @Override
    public String toString() {
        return String.format("%s (%d)", fullName, age);
    }
}