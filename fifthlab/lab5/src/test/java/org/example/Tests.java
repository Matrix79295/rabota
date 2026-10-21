package org.example;

import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.entity.EmploymentRecord;
import org.example.service.DepartmentFileReader;
import org.example.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Tests {

    private Department department;
    private DepartmentService service;

    @BeforeEach
    public void setUp() {
        // Создаем файл программно, чтобы не зависеть от папки resources
        String content = "POSITION;Менеджер\n" +
                "RECORD;Иванов Иван;30;2020-01-15;2022-06-30\n" +
                "RECORD;Петров Петр;25;2023-03-01;\n" +
                "RECORD;Сидоров Сидор;notANumber;2021-01-01;\n" +
                "RECORD;Ошибкин Олег;40;INVALID_DATE;\n" +
                "POSITION;Разработчик\n" +
                "RECORD;Смирнова Анна;28;2019-09-01;\n" +
                "RECORD;Козлов Дмитрий;35;2018-04-10;2021-12-31\n" +
                "RECORD;БезДаты Борис;22;\n" +
                "WRONG;строка;полностью;битая\n" +
                "POSITION;\n" +
                "POSITION;Тестировщик\n" +
                "RECORD;Тестов Тест;29;2022-02-02;2023-02-02\n";

        try {
            Files.writeString(Path.of("department.txt"), content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        department = new DepartmentFileReader().read("department.txt");
        service = new DepartmentService();
    }

    @Test
    public void testFileLoadedCorrectly() {
        Assertions.assertEquals(3, department.getPositions().size());
        Assertions.assertTrue(department.findPosition("Менеджер").isPresent());
        Assertions.assertTrue(department.findPosition("Разработчик").isPresent());
        Assertions.assertTrue(department.findPosition("Тестировщик").isPresent());
    }

    @Test
    public void testInvalidPositionIgnored() {
        Assertions.assertEquals(0, department.getPositions().stream()
                .filter(p -> p.getTitle().isBlank()).count());
    }

    @Test
    public void testInvalidRecordsSkipped() {
        Department.Position manager = department.findPosition("Менеджер").orElseThrow();
        Assertions.assertEquals(2, manager.getHistory().size());
        List<String> names = manager.allEmployees().stream()
                .map(Employee::getFullName)
                .collect(java.util.stream.Collectors.toList());

        Assertions.assertTrue(names.contains("Иванов Иван"));
        Assertions.assertTrue(names.contains("Петров Петр"));
        Assertions.assertFalse(names.contains("Сидоров Сидор"));
        Assertions.assertFalse(names.contains("Ошибкин Олег"));
    }

    @Test
    public void testCurrentEmployees() {
        Department.Position manager = department.findPosition("Менеджер").orElseThrow();
        List<Employee> current = manager.currentEmployees();
        Assertions.assertEquals(1, current.size());
        Assertions.assertEquals("Петров Петр", current.get(0).getFullName());
    }

    @Test
    public void testAllUniqueEmployees() {
        Assertions.assertEquals(5, service.allEmployees(department).size());
    }

    @Test
    public void testPositionInnerClassKnowsDepartment() {
        Department.Position p = department.findPosition("Менеджер").orElseThrow();
        Assertions.assertSame(p.getDepartment(), department);
    }

    @Test
    public void testSortByDurationAnonymousComparator() {
        Department.Position manager = department.findPosition("Менеджер").orElseThrow();
        List<EmploymentRecord> sorted = service.sortedHistoryByDuration(manager);
        for (int i = 1; i < sorted.size(); i++) {
            Assertions.assertTrue(sorted.get(i - 1).durationDays() <= sorted.get(i).durationDays());
        }
    }

    @Test
    public void testSortByNameAnonymousComparator() {
        Department.Position manager = department.findPosition("Менеджер").orElseThrow();
        List<EmploymentRecord> sorted = service.sortedHistoryByName(manager);
        for (int i = 1; i < sorted.size(); i++) {
            Assertions.assertTrue(
                    sorted.get(i - 1).getEmployee().getFullName()
                            .compareToIgnoreCase(sorted.get(i).getEmployee().getFullName()) <= 0);
        }
    }

    @Test
    public void testAnonymousRunnableReport() {
        Department.Position dev = department.findPosition("Разработчик").orElseThrow();
        String report = service.buildPositionReport(dev);
        Assertions.assertTrue(report.contains("Разработчик"));
    }

    @Test
    public void testRecordDurationActiveHasPositiveDays() {
        Department.Position manager = department.findPosition("Менеджер").orElseThrow();
        EmploymentRecord active = manager.getHistory().stream()
                .filter(EmploymentRecord::isActive).findFirst().orElseThrow();
        Assertions.assertTrue(active.durationDays() > 0);
    }
}