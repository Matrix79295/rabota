package org.example.service;
import org.example.creator.EmploymentRecordCreator;
import org.example.creator.RecordFactory;
import org.example.creator.RecordFactory.Type;
import org.example.entity.Department;
import org.example.entity.EmploymentRecord;
import org.example.exception.InvalidDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
public class DepartmentFileReader {
    private static final Logger log = LogManager.getLogger(DepartmentFileReader.class);
    public Department read(String path) {
        Department dept = new Department("IT-отдел");
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            Department.Position current = null;
            int lineNo = 0;
            for (String line : lines) {
                lineNo++;
                try {
                    RecordFactory.ParsedLine parsed = RecordFactory.parse(line);
                    if (parsed.type == Type.POSITION_STR) {
                        if (parsed.fields.length < 2 || parsed.fields[1].isBlank())
                            throw new InvalidDataException("Пустое название должности");
                        current = dept.addPosition(parsed.fields[1].trim());
                    } else if (parsed.type == Type.RECORD) {
                        if (current == null)
                            throw new InvalidDataException("RECORD без предшествующей POSITION");
                        String[] rest = new String[parsed.fields.length - 1];
                        System.arraycopy(parsed.fields, 1, rest, 0, rest.length);
                        EmploymentRecord r = new EmploymentRecordCreator().create(rest);
                        current.addRecord(r);
                    }
                } catch (InvalidDataException e) {
                    log.warn("Строка {} пропущена: {} — {}", lineNo, line, e.getMessage());
                }
            }
            log.info("Файл {} прочитан, должностей: {}", path, dept.getPositions().size());
        } catch (IOException e) {
            log.error("Ошибка чтения файла {}: {}", path, e.getMessage(), e);
        }
        return dept;
    }
}