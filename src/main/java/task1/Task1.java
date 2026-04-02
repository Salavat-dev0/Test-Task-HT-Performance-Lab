package task1;

import java.util.ArrayList;
import java.util.List;

public class Task1 {

    static void main(String[] args) {
        if (args.length % 2 != 0) {
            System.out.println("Ошибка: количество аргументов должно быть четным (пары n и m)");
            return;
        }

        // Собираем пары (n, m) в список
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < args.length; i += 2) {
            try {
                int n = Integer.parseInt(args[i]);
                int m = Integer.parseInt(args[i + 1]);
                pairs.add(new Pair(n, m));
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является целым числом");
            }
        }

        // Параллельная обработка пар
        String combinedPath = pairs.parallelStream()
                .map(pair -> buildPath(pair.n, pair.m))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        System.out.println(combinedPath);
    }

    /**
     * Строит путь для кругового массива чисел от 1 до n, двигаясь интервалами длины m.
     */
    private static String buildPath(int n, int m) {
        if (n <= 0 || m <= 0) {
            return "";
        }

        StringBuilder path = new StringBuilder();
        int currentIndex = 0;

        do {
            path.append(currentIndex + 1);
            currentIndex = (currentIndex + m - 1) % n;
        } while (currentIndex != 0);

        return path.toString();
    }

    /**
     * Вспомогательный класс для хранения пары.
     */
    static class Pair {
        int n;
        int m;

        public Pair(int n, int m) {
            this.n = n;
            this.m = m;
        }
    }
}
