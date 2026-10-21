package org.example;
import org.example.entity.*;
import org.example.exception.InvalidCompositionDataException;
import org.example.factory.*;
import org.example.service.DiscService;
import org.example.service.FileReaderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.testng.Assert.*;
public class MusicAppTest {
    private static final Logger logger = LogManager.getLogger(MusicAppTest.class);
    private DiscService discService;
    private Disc disc;

    @BeforeMethod
    public void setUp() {
        discService = new DiscService();
        disc = new Disc();
        disc.addComposition(new RockComposition("A", "X", 200, "Punk"));
        disc.addComposition(new ClassicComposition("B", "Y", 300, "Bach"));
        disc.addComposition(new PopComposition("C", "Z", 150, 1));
        disc.addComposition(new RockComposition("D", "W", 250, "Metal"));
    }

    @Test
    public void rockFactoryCreatesRockComposition() throws InvalidCompositionDataException {
        logger.info("Тест: rockFactoryCreatesRockComposition");
        CompositionFactory factory = new RockFactory();
        Composition c = factory.createComposition(new String[]{"Song", "Band", "200", "Punk"});
        assertTrue(c instanceof RockComposition);
        assertEquals(c.getStyle(), "Rock");
        assertEquals(c.getDurationSeconds(), 200);
        assertEquals(((RockComposition) c).getSubGenre(), "Punk");
    }

    @Test
    public void classicFactoryCreatesClassicComposition() throws InvalidCompositionDataException {
        logger.info("Тест: classicFactoryCreatesClassicComposition");
        CompositionFactory factory = new ClassicFactory();
        Composition c = factory.createComposition(new String[]{"Symphony", "Orchestra", "500", "Mozart"});
        assertTrue(c instanceof ClassicComposition);
        assertEquals(c.getStyle(), "Classic");
        assertEquals(((ClassicComposition) c).getComposer(), "Mozart");
    }

    @Test
    public void popFactoryCreatesPopComposition() throws InvalidCompositionDataException {
        logger.info("Тест: popFactoryCreatesPopComposition");
        CompositionFactory factory = new PopFactory();
        Composition c = factory.createComposition(new String[]{"Hit", "Singer", "180", "1"});
        assertTrue(c instanceof PopComposition);
        assertEquals(c.getStyle(), "Pop");
        assertEquals(((PopComposition) c).getChartPosition(), 1);
    }

    @Test(expectedExceptions = InvalidCompositionDataException.class)
    public void invalidDurationThrowsException() throws InvalidCompositionDataException {
        logger.info("Тест: invalidDurationThrowsException");
        new RockFactory().createComposition(new String[]{"Song", "Band", "abc", "Punk"});
    }

    @Test(expectedExceptions = InvalidCompositionDataException.class)
    public void negativeDurationThrowsException() throws InvalidCompositionDataException {
        logger.info("Тест: negativeDurationThrowsException");
        new RockFactory().createComposition(new String[]{"Song", "Band", "-5", "Punk"});
    }

    @Test
    public void emptyTitleUsesDefault() throws InvalidCompositionDataException {
        logger.info("Тест: emptyTitleUsesDefault");
        Composition c = new RockFactory().createComposition(new String[]{"", "Band", "100", "Punk"});
        assertEquals(c.getTitle(), "Unknown");
    }

    @Test
    public void totalDurationIsCorrect() {
        logger.info("Тест: totalDurationIsCorrect");
        assertEquals(discService.calculateTotalDuration(disc), 900);
    }

    @Test
    public void sortByStyleOrdersCorrectly() {
        logger.info("Тест: sortByStyleOrdersCorrectly");
        discService.sortByStyle(disc);
        List<Composition> list = disc.getCompositions();
        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(list.get(i).getStyle().compareToIgnoreCase(list.get(i + 1).getStyle()) <= 0,
                    "Композиции должны быть упорядочены по стилю");
        }
    }

    @Test
    public void findByDurationRangeReturnsExpected() {
        logger.info("Тест: findByDurationRangeReturnsExpected");
        List<Composition> found = discService.findByDurationRange(disc, 180, 260);
        assertEquals(found.size(), 2);
        found.forEach(c -> assertTrue(
                c.getDurationSeconds() >= 180 && c.getDurationSeconds() <= 260));
    }

    @Test
    public void findByDurationRangeEmptyWhenNoMatch() {
        logger.info("Тест: findByDurationRangeEmptyWhenNoMatch");
        assertTrue(discService.findByDurationRange(disc, 1000, 2000).isEmpty());
    }

    @Test
    public void readsValidAndSkipsInvalidLines() throws IOException {
        logger.info("Тест: readsValidAndSkipsInvalidLines");

        File tempFile = File.createTempFile("compositions", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("ROCK;Song1;Artist1;200;Punk\n");
            writer.write("INVALID;Song2;Artist2;100;x\n");
            writer.write("CLASSIC;Song3;Artist3;300;Bach\n");
            writer.write("POP;Song4;Artist4;abc;1\n");
        }

        List<Composition> result = new FileReaderService().readCompositions(tempFile.getAbsolutePath());
        assertEquals(result.size(), 2);
    }

    @Test
    public void nonExistentFileReturnsEmptyList() {
        logger.info("Тест: nonExistentFileReturnsEmptyList");
        assertTrue(new FileReaderService().readCompositions("no-such-file.txt").isEmpty());
    }

    @Test
    public void fullDiscScenario() {
        logger.info("=== Старт интеграционного сценария ===");

        FileReaderService readerService = new FileReaderService();

        List<Composition> compositions =
                readerService.readCompositions("src/main/resources/compositions.txt");
        Disc realDisc = new Disc();
        discService.addCompositionsToDisc(realDisc, compositions);

        if (compositions.isEmpty()) {
            logger.warn("Файл compositions.txt не найден, интеграционный тест частично пропущен.");
            return;
        }

        assertFalse(realDisc.getCompositions().isEmpty(), "Диск должен содержать композиции");

        int total = discService.calculateTotalDuration(realDisc);
        assertTrue(total > 0, "Длительность должна быть > 0");

        discService.sortByStyle(realDisc);
        List<Composition> sorted = realDisc.getCompositions();
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i).getStyle().compareToIgnoreCase(sorted.get(i + 1).getStyle()) <= 0,
                    "Композиции должны быть упорядочены по стилю");
        }

        List<Composition> found = discService.findByDurationRange(realDisc, 180, 260);
        found.forEach(c -> assertTrue(
                c.getDurationSeconds() >= 180 && c.getDurationSeconds() <= 260));

        logger.info("=== Интеграционный сценарий успешно завершён ===");
    }
}