package org.example.entity;
import java.time.LocalDate;
import java.util.Objects;
public class EmploymentRecord {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    public EmploymentRecord(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Employee getEmployee() { return employee; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isActive() { return endDate == null; }
    public long durationDays() {
        LocalDate end = (endDate == null) ? LocalDate.now() : endDate;
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmploymentRecord)) return false;
        EmploymentRecord r = (EmploymentRecord) o;
        return Objects.equals(employee, r.employee)
                && Objects.equals(startDate, r.startDate)
                && Objects.equals(endDate, r.endDate);
    }

    @Override
    public int hashCode() { return Objects.hash(employee, startDate, endDate); }

    @Override
    public String toString() {
        return String.format("%s: %s → %s%s",
                employee.getFullName(), startDate,
                endDate == null ? "по наст. время" : endDate,
                isActive() ? " [активен]" : "");
    }
}