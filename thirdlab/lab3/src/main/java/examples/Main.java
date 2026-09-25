package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Circle> circles = new ArrayList<>();

        System.out.print("Сколько окружностей ввести? ");
        int n = readInt(sc);

        for (int i = 0; i < n; i++) {
            System.out.print("\n--- Окружность #" + (i + 1) + " ---");

            System.out.print("  x центра: ");
            Rational x = readRational(sc);

            System.out.print("  y центра: ");
            Rational y = readRational(sc);

            System.out.print("  радиус: ");
            double r = readDouble(sc);

            circles.add(new Circle(new Point(x, y), r));
        }

        sc.close();

        System.out.println("\n=== Введённые окружности ===");
        for (Circle c : circles) {
            System.out.println(c);
        }

        System.out.print("\n=== Группы окружностей, центры которых лежат на одной прямой ===");
        List<List<Circle>> groups = Circles.groups(circles);
        if (groups.isEmpty()) {
            System.out.println("  Групп не найдено.");
        } else {
            for (int i = 0; i < groups.size(); i++) {
                System.out.println("Группа " + (i + 1) + ":");
                for (Circle c : groups.get(i)) {
                    System.out.println("  " + c);
                }
            }
        }

        System.out.print("\n=== Экстремумы ===");
        System.out.print("\nMax по площади: " + Circles.maxArea(circles));
        System.out.print("\nMin по площади: " + Circles.minArea(circles));
        System.out.print("\nMax по периметру: " + Circles.maxPer(circles));
        System.out.print("\nMin по периметру: " + Circles.minPer(circles));
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("  Не число, попробуйте снова: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim().replace(',', '.');
            try {
                double v = Double.parseDouble(line);
                if (v <= 0) {
                    System.out.print("  Радиус должен быть > 0: ");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print("  Не число, попробуйте снова: ");
            }
        }
    }

    private static Rational readRational(Scanner sc) {
        while (true) {
            String line = sc.nextLine().trim();
            try {
                if (line.contains("/")) {
                    String[] parts = line.split("/");
                    long num = Long.parseLong(parts[0].trim());
                    long den = Long.parseLong(parts[1].trim());
                    return new Rational(num, den);
                } else {
                    long num = Long.parseLong(line);
                    return new Rational(num);
                }
            } catch (Exception e) {
                System.out.print("  Неверный формат, попробуйте снова (например, 1/2 или 3): ");
            }
        }
    }
}