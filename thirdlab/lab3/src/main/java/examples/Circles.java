package examples;
import java.util.*;
public class Circles {
    static boolean collinear(Point a, Point b, Point c) {
        double ax = a.x.toDouble(), ay = a.y.toDouble();
        double bx = b.x.toDouble(), by = b.y.toDouble();
        double cx = c.x.toDouble(), cy = c.y.toDouble();
        double eps = 1e-9;
        return Math.abs((bx - ax) * (cy - ay) - (by - ay) * (cx - ax)) < eps;
    }

    public static List<List<Circle>> groups(List<Circle> cs) {
        List<List<Circle>> result = new ArrayList<>();
        int n = cs.size();
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++)
                    if (collinear(cs.get(i).center, cs.get(j).center, cs.get(k).center)) {
                        List<Circle> g = new ArrayList<>();
                        for (Circle c : cs)
                            if (collinear(cs.get(i).center, cs.get(j).center, c.center))
                                g.add(c);
                        boolean dup = false;
                        for (List<Circle> old : result)
                            if (old.size() == g.size() && new HashSet<>(old).containsAll(g)) { dup = true; break; }
                        if (!dup) result.add(g);
                    }
        return result;
    }

    public static Circle maxArea(List<Circle> cs) {
        return cs.stream().max(Comparator.comparingDouble(Circle::area)).orElseThrow();
    }

    public static Circle minArea(List<Circle> cs) {
        return cs.stream().min(Comparator.comparingDouble(Circle::area)).orElseThrow();
    }

    public static Circle maxPer(List<Circle> cs) {
        return cs.stream().max(Comparator.comparingDouble(Circle::perimeter)).orElseThrow();
    }

    public static Circle minPer(List<Circle> cs) {
        return cs.stream().min(Comparator.comparingDouble(Circle::perimeter)).orElseThrow();
    }
}