// 5. Найти все p-значные числа из заданной последовательности натуральных чисел, в
// записи которых не повторяются цифры, и подсчитать их количество.
import java.util.Scanner;
public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int size = scanner.nextInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int count = 0;
        for (int i = 0; i < size; i++) {
            int num = array[i];
            if (String.valueOf(num).length() == p) {
                boolean[] digits = new boolean[10];
                int temp = num;
                boolean isUnique = true;
                while (temp > 0) {
                    int digit = temp % 10;
                    if (digits[digit]) {
                        isUnique = false;
                        break;
                    }
                    digits[digit] = true;
                    temp /= 10;
                }
                if (isUnique) {
                    System.out.print(num + " ");
                    count++;
                }
            }
        }
        System.out.print(count);
    }
}