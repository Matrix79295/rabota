// 17.Преобразовать строки матрицы таким образом, чтобы элементы,
//равные нулю, располагались после всех остальных. Удалить нулевые столбцы
import java.util.*;
import java.util.stream.IntStream;
public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        int[][] rearranged = Arrays.stream(matrix)
                .map(row -> IntStream.concat(
                        Arrays.stream(row).filter(x -> x != 0),
                        Arrays.stream(row).filter(x -> x == 0)
                ).toArray())
                .toArray(int[][]::new);
        int columns = rearranged[0].length;
        int[] keepCols = IntStream.range(0, cols)
                .filter(j -> Arrays.stream(rearranged)
                        .anyMatch(row -> row[j] != 0))
                .toArray();
        int[][] result = Arrays.stream(rearranged)
                .map(row -> Arrays.stream(keepCols)
                        .map(j -> row[j])
                        .toArray())
                .toArray(int[][]::new);
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}