package examples;
public class Point {
    final Rational x, y;
    public Point(Rational x, Rational y) { this.x = x; this.y = y; }

    @Override public String toString() { return "(" + x + "; " + y + ")"; }
}