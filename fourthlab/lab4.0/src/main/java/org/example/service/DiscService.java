package org.example.service;

import org.example.comparator.DurationComparator;
import org.example.comparator.StyleComparator;
import org.example.entity.Composition;
import org.example.entity.Disc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class DiscService {
    private static final Logger logger = LogManager.getLogger(DiscService.class);

    public void addCompositionsToDisc(Disc disc, List<Composition> compositions) {
        logger.debug("Добавление {} композиций на диск", compositions.size());
        compositions.forEach(disc::addComposition);
        logger.info("На диск добавлено {} композиций. Всего на диске: {}",
                compositions.size(), disc.getCompositions().size());
    }

    public int calculateTotalDuration(Disc disc) {
        int total = disc.getTotalDuration();
        logger.info("Общая продолжительность диска: {} сек. ({} мин. {} сек.)",
                total, total / 60, total % 60);
        return total;
    }

    public void sortByStyle(Disc disc) {
        logger.debug("Сортировка диска по стилю");
        List<Composition> sorted = disc.getCompositions()
                .stream()
                .sorted(new StyleComparator())
                .collect(Collectors.toList());
        disc.setCompositions(sorted);
        logger.info("Диск отсортирован по стилю. Порядок:");
        sorted.forEach(c -> logger.info("  -> {}", c));
    }

    public List<Composition> findByDurationRange(Disc disc, int min, int max) {
        logger.debug("Поиск композиций в диапазоне {}..{} сек.", min, max);
        List<Composition> result = disc.getCompositions()
                .stream()
                .filter(c -> c.getDurationSeconds() >= min && c.getDurationSeconds() <= max)
                .sorted(new DurationComparator())
                .collect(Collectors.toList());
        logger.info("Найдено {} композиций в диапазоне {}..{} сек.", result.size(), min, max);
        result.forEach(c -> logger.info("  -> {}", c));
        return result;
    }
}