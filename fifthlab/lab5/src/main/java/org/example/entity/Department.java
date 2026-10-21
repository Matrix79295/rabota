package org.example.entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
public class Department {
    private static final Logger log = LogManager.getLogger(Department.class);
    private final String name;
    private final List<Position> positions = new ArrayList<>();
    public Department(String name) {
        this.name = name;
        log.info("Создан отдел '{}'", name);
    }
    public String getName() { return name; }

    public Position addPosition(String title) {
        Position p = new Position(title);
        positions.add(p);
        log.debug("В отдел '{}' добавлена должность '{}'", name, title);
        return p;
    }
    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }
    public Optional<Position> findPosition(String title) {
        return positions.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
    public class Position {
        private final String title;
        private final List<EmploymentRecord> history = new ArrayList<>();
        private Position(String title) {
            this.title = title;
        }
        public String getTitle() { return title; }
        public Department getDepartment() { return Department.this; }
        public void addRecord(EmploymentRecord record) {
            history.add(record);
            log.debug("В отделе '{}' на должность '{}' добавлен сотрудник: {}",
                    name, title, record.getEmployee().getFullName());
        }
        public List<EmploymentRecord> getHistory() {
            return Collections.unmodifiableList(history);
        }
        public List<Employee> currentEmployees() {
            List<Employee> result = new ArrayList<>();
            for (EmploymentRecord r : history) {
                if (r.isActive()) result.add(r.getEmployee());
            }
            return result;
        }
        public List<Employee> allEmployees() {
            List<Employee> result = new ArrayList<>();
            for (EmploymentRecord r : history) result.add(r.getEmployee());
            return result;
        }

        @Override
        public String toString() {
            return String.format("Position[%s в '%s', история=%d]",
                    title, name, history.size());
        }
    }
}