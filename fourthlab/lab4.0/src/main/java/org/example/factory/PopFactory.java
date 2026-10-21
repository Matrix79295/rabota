package org.example.factory;
import org.example.entity.Composition;
import org.example.entity.PopComposition;
import org.example.exception.InvalidCompositionDataException;
public class PopFactory extends CompositionFactory {

    @Override
    public Composition createComposition(String[] data) throws InvalidCompositionDataException {
        logger.debug("PopFactory: создание композиции из данных");
        String title = validateString(data.length > 0 ? data[0] : null, "Unknown");
        String artist = validateString(data.length > 1 ? data[1] : null, "Unknown");
        int duration = validateInt(data.length > 2 ? data[2] : "0", 0);
        int chartPosition = validateInt(data.length > 3 ? data[3] : "0", 0);
        logger.info("Создана pop-композиция: '{}' ({}), {} сек., позиция в чарте: {}",
                title, artist, duration, chartPosition);
        return new PopComposition(title, artist, duration, chartPosition);
    }
}