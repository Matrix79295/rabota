package examples;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
public class CirclesTest {

    private Circle c(long x, long y, double r) {
        return new Circle(new Point(new Rational(x), new Rational(y)), r);
    }

    @Test public void rationalReduces() {
        Rational r = new Rational(4, 8);
        assertEquals(1, r.num);
        assertEquals(2, r.den);
    }

    @Test public void rationalZeroDen() {
        try {
            new Rational(1, 0);
            fail("Ожидалось исключение");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test public void areaAndPerimeter() {
        Circle c = c(0, 0, 2);
        assertEquals(Math.PI * 4, c.area(), 1e-9);
        assertEquals(2 * Math.PI * 2, c.perimeter(), 1e-9);
    }

    @Test public void negativeRadius() {
        try {
            new Circle(null, -1);
            fail("Ожидалось исключение");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test public void collinearTrue() {
        assertTrue(Circles.collinear(
                new Point(new Rational(0), new Rational(0)),
                new Point(new Rational(1), new Rational(1)),
                new Point(new Rational(2), new Rational(2))));
    }

    @Test public void collinearFalse() {
        assertFalse(Circles.collinear(
                new Point(new Rational(0), new Rational(0)),
                new Point(new Rational(1), new Rational(2)),
                new Point(new Rational(2), new Rational(3))));
    }

    @Test public void oneGroup() {
        List<Circle> cs = List.of(c(0, 0, 1), c(1, 1, 2), c(2, 2, 3), c(5, 5, 1));
        assertEquals(1, Circles.groups(cs).size());
        assertEquals(4, Circles.groups(cs).get(0).size());
    }

    @Test public void noGroups() {
        List<Circle> cs = List.of(c(0, 0, 1), c(1, 2, 1));
        assertTrue(Circles.groups(cs).isEmpty());
    }

    @Test public void maxMin() {
        List<Circle> cs = List.of(c(0, 0, 1), c(1, 1, 5), c(2, 2, 3));
        assertEquals(5, Circles.maxArea(cs).radius, 1e-9);
        assertEquals(1, Circles.minArea(cs).radius, 1e-9);
        assertEquals(5, Circles.maxPer(cs).radius, 1e-9);
        assertEquals(1, Circles.minPer(cs).radius, 1e-9);
    }
}