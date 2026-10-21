package org.example.service;
import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.entity.EmploymentRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class DepartmentService {
    private static final Logger log = LogManager.getLogger(DepartmentService.class);
    private final EmployeeDurationComparator comparators = new EmployeeDurationComparator();
    public List<Employee> allEmployees(Department dept) {
        List<Employee> result = new ArrayList<>();
        for (Department.Position p : dept.getPositions()) {
            for (Employee e : p.allEmployees()) {
                if (!result.contains(e)) result.add(e);
            }
        }
        log.info("Уникальных сотрудников в отделе '{}': {}", dept.getName(), result.size());
        return result;
    }
    public List<EmploymentRecord> sortedHistoryByDuration(Department.Position position) {
        List<EmploymentRecord> history = new ArrayList<>(position.getHistory());
        history.sort(comparators.byDurationAsc());
        return history;
    }
    public List<EmploymentRecord> sortedHistoryByName(Department.Position position) {
        List<EmploymentRecord> history = new ArrayList<>(position.getHistory());
        history.sort(comparators.byEmployeeName());
        return history;
    }
    public String buildPositionReport(Department.Position position) {
        final StringBuilder sb = new StringBuilder();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                sb.append("Должность: ").append(position.getTitle()).append("\n");
                sb.append("Текущие: ").append(position.currentEmployees()).append("\n");
                sb.append("Всего когда-либо: ").append(position.allEmployees().size()).append("\n");
            }
        };
        action.run();
        return sb.toString();
    }
}