package org.example.factory;

import org.example.entity.Composition;
import org.example.entity.RockComposition;
import org.example.exception.InvalidCompositionDataException;

public class RockFactory extends CompositionFactory {
    @Override
    public Composition createComposition(String[] data) throws InvalidCompositionDataException {
        logger.debug("RockFactory: создание композиции из данных");
        String title = validateString(data.length > 0 ? data[0] : null, "Unknown");
        String artist = validateString(data.length > 1 ? data[1] : null, "Unknown");
        int duration = validateInt(data.length > 2 ? data[2] : "0", 0);
        String subGenre = validateString(data.length > 3 ? data[3] : null, "Hard Rock");
        logger.info("Создана rock-композиция: '{}' ({}), {} сек., поджанр: {}",
                title, artist, duration, subGenre);
        return new RockComposition(title, artist, duration, subGenre);
    }
}