package org.example.factory;
import org.example.entity.Composition;
import org.example.exception.InvalidCompositionDataException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public abstract class CompositionFactory {
    protected static final Logger logger = LogManager.getLogger(CompositionFactory.class);
    public abstract Composition createComposition(String[] data)
            throws InvalidCompositionDataException;
    protected String validateString(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            logger.warn("Пустое строковое значение, используется значение по умолчанию: '{}'", defaultValue);
            return defaultValue;
        }
        return value.trim();
    }
    protected int validateInt(String value, int defaultValue)
            throws InvalidCompositionDataException {
        if (value == null || value.isBlank()) {
            logger.warn("Пустое числовое значение, используется значение по умолчанию: {}", defaultValue);
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 0) {
                throw new InvalidCompositionDataException("Отрицательное значение: " + value);
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new InvalidCompositionDataException(
                    "Некорректное числовое значение: " + value, e);
        }
    }
}