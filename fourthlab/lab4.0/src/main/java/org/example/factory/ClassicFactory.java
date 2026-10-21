package org.example.factory;
import org.example.entity.ClassicComposition;
import org.example.entity.Composition;
import org.example.exception.InvalidCompositionDataException;
public class ClassicFactory extends CompositionFactory {

    @Override
    public Composition createComposition(String[] data) throws InvalidCompositionDataException {
        logger.debug("ClassicFactory: создание композиции из данных");
        String title = validateString(data.length > 0 ? data[0] : null, "Unknown");
        String artist = validateString(data.length > 1 ? data[1] : null, "Unknown");
        int duration = validateInt(data.length > 2 ? data[2] : "0", 0);
        String composer = validateString(data.length > 3 ? data[3] : null, "Unknown composer");
        logger.info("Создана classic-композиция: '{}' ({}), {} сек., композитор: {}",
                title, artist, duration, composer);
        return new ClassicComposition(title, artist, duration, composer);
    }
}