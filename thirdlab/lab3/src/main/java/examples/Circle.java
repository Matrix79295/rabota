package examples;
public class Circle {
    final Point center;
    final double radius;

    public Circle(Point center, double radius) {
        if (radius <= 0) throw new IllegalArgumentException("radius<=0");
        this.center = center;
        this.radius = radius;
    }

    public double area()      { return Math.PI * radius * radius; }
    public double perimeter() { return 2 * Math.PI * radius; }

    @Override public String toString() {
        return String.format("Circle%sr=%.2f area=%.3f per=%.3f",
                center, radius, area(), perimeter());
    }
}