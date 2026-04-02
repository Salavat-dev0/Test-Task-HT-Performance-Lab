package task4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Task4 {

    static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Не указан файл с данными.");
            return;
        }

        try {
            // Элементы массива читаются из файла, переданного в качестве аргумента командной строки
            String filenamePath = "task4/" + args[0];
            List<Integer> list = new ArrayList<>();

            try (Scanner sc = new Scanner(new File(filenamePath))) {
                while (sc.hasNextInt()) {
                    list.add(sc.nextInt());
                }
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + filenamePath);
            }

            // Поиск минимального количества ходов
            int[] numbers = list.stream().mapToInt(Integer::intValue).toArray();
            Arrays.sort(numbers);

            int n = numbers.length;
            int median = numbers[n / 2];

            long moves = 0;
            for (int num : numbers) {
                moves += Math.abs(num - median);
            }

            if (moves <= 20) {
                System.out.println(moves);
            } else {
                System.out.println("20 ходов недостаточно для приведения всех элементов массива к одному числу");
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

    }
}
