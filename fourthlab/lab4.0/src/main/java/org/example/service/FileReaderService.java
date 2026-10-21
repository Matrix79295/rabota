package org.example.service;
import org.example.entity.Composition;
import org.example.exception.InvalidCompositionDataException;
import org.example.factory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class FileReaderService {
    private static final Logger logger = LogManager.getLogger(FileReaderService.class);
    public List<Composition> readCompositions(String filePath) {
        List<Composition> result = new ArrayList<>();
        logger.info("Начало чтения файла композиций: {}", filePath);
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            int lineNumber = 0;
            for (String line : lines) {
                lineNumber++;
                if (line == null || line.isBlank()) {
                    logger.debug("Строка {} пустая — пропущена", lineNumber);
                    continue;
                }
                try {
                    Composition composition = parseLine(line);
                    result.add(composition);
                    logger.info("Строка {}: успешно создана композиция — {}", lineNumber, composition);
                } catch (InvalidCompositionDataException e) {
                    logger.error("Строка {}: не удалось создать композицию ({}). Данные: '{}'",
                            lineNumber, e.getMessage(), line);
                }
            }
        } catch (IOException e) {
            logger.fatal("Не удалось прочитать файл: {}", filePath, e);
        }
        logger.info("Чтение завершено. Успешно создано композиций: {}", result.size());
        return result;
    }
    private Composition parseLine(String line) throws InvalidCompositionDataException {
        String[] parts = line.split(";");
        if (parts.length < 2) {
            throw new InvalidCompositionDataException("Недостаточно данных в строке: " + line);
        }
        CompositionFactory factory = resolveFactory(parts[0].trim());
        String[] payload = new String[parts.length - 1];
        System.arraycopy(parts, 1, payload, 0, payload.length);
        return factory.createComposition(payload);
    }
    private CompositionFactory resolveFactory(String type) throws InvalidCompositionDataException {
        switch (type.toUpperCase()) {
            case "ROCK":    return new RockFactory();
            case "CLASSIC": return new ClassicFactory();
            case "POP":     return new PopFactory();
            default:
                throw new InvalidCompositionDataException("Неизвестный тип композиции: " + type);
        }
    }
}