//29. Для каждого числа из заданной последовательности натуральных чисел
//определить, равна ли его первая цифра 5 и все цифры различны
import java.util.Scanner;
public class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        for (int i = 0; i < size; i++) {
            int num = array[i];
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
                    int number = array[i];
                    while (String.valueOf(number).length() != 1){
                        number /= 10;
                    }
                    if (number == 5) {
                        System.out.print(array[i] + " ");
                    }
                }
            }
    }
}