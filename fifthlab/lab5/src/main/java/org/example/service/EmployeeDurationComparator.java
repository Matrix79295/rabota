package org.example.service;
import org.example.entity.EmploymentRecord;
import java.util.Comparator;
public class EmployeeDurationComparator {
    public Comparator<EmploymentRecord> byDurationAsc() {
        return new Comparator<EmploymentRecord>() {
            @Override
            public int compare(EmploymentRecord a, EmploymentRecord b) {
                return Long.compare(a.durationDays(), b.durationDays());
            }
        };
    }
    public Comparator<EmploymentRecord> byEmployeeName() {
        return new Comparator<EmploymentRecord>() {
            @Override
            public int compare(EmploymentRecord a, EmploymentRecord b) {
                return a.getEmployee().getFullName()
                        .compareToIgnoreCase(b.getEmployee().getFullName());
            }
        };
    }
}