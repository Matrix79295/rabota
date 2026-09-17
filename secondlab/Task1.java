//5.Дана целочисленная матрица размера A(n,m). Построить на
//основе её «зубчатую матрицу» в которой каждая строка новой матрицы состоит
//из элементов соответствующей строки исходной матрицы взятых в
//единственном экземпляре. Отсортировать строки матрицы по количеству
//отрицательных элементов в ней.
import java.util.*;
import java.util.stream.Collectors;
public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        List<int[]> sortMatrix = Arrays.stream(matrix) // Stream<int[]>
                .map(row -> Arrays.stream(row)
                        .distinct()
                        .toArray()
                )
                .sorted(Comparator.comparingLong(row ->
                        Arrays.stream(row)
                                .filter(x -> x < 0)
                                .count()
                ))
                .collect(Collectors.toList());
        for (int[] row : sortMatrix) {
            System.out.println(Arrays.toString(row));
        }
    }
}