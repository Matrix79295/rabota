//17. Для каждого числа из заданной последовательности натуральных чисел найти
//произведение цифр, находящихся на нечётных позициях (нумерация позиций идёт слева
//направо).
import java.util.Scanner;
public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
            int mul = 1;
            int num = array[i];
            int length = String.valueOf(num).length();
            if (length % 2 == 1) {
                while (num != 0) {
                    mul *= num % 10;
                    num /= 100;
                }
            }
            else {
                while (num != 0) {
                    num /= 10;
                    mul *= num % 10;
                    num /= 10;
                }
            }
            System.out.print(mul + " ");
        }
    }
}