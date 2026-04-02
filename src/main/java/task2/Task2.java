package task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task2 {

    static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Ошибка: необходимо передать два аргумента - пути к файлам с эллипсом и точками.");
            return;
        }

        String ellipsePath = "task2/" + args[0];
        String pointsPath = "task2/" + args[1];

        double[] ellipseParams = readEllipseFile(ellipsePath);
        if (ellipseParams == null) {
            System.err.println("Не удалось прочитать параметры эллипса. Проверьте файлы.");
            return;
        }
        double cx = ellipseParams[0];
        double cy = ellipseParams[1];
        double a = ellipseParams[2];
        double b = ellipseParams[3];

        List<double[]> points = readPoints(pointsPath);
        for (double[] point : points) {
            double x  = point[0];
            double y = point[1];
            int result = classifyPoints(cx, cy, a, b, x, y);
            System.out.println(result);
        }
    }

    /**
     * Чтение файла с параметрами эллипса.
     * @param ellipseFilename название файла с параметрами эллипса.
     * @return массив с параметрами эллипса.
     */
    private static double[] readEllipseFile(String ellipseFilename) {
        try (Scanner sc = new Scanner(new File(ellipseFilename))) {
            if (!sc.hasNextLine()) {
                System.out.println("Файл эллипса пуст.");
                return null;
            }

            // Чтение центра эллипса
            String centerLine = sc.nextLine().trim();
            String[] centerXY =  centerLine.split(" ");

            double cx =  Double.parseDouble(centerXY[0]);
            double cy = Double.parseDouble(centerXY[1]);

            // Чтение радиуса эллипса
            String radiusLine = sc.nextLine().trim();
            String[] radiusXY =  radiusLine.split(" ");

            double a = Double.parseDouble(radiusXY[0]);
            double b = Double.parseDouble(radiusXY[1]);

            // Параметры эллипса
            return new double[]{cx, cy, a, b};

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден: " + e.getMessage());
        }
        return null;
    }

    /**
     * Чтение файла с координатами точек.
     * @param pointsFilename название файла с координатами точек.
     * @return массив с координатами точек (x, y).
     */
    private static List<double[]> readPoints(String pointsFilename) {
        List<double[]> points = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(pointsFilename))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" ");

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                points.add(new double[]{x, y});
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не найден: " + e.getMessage());
        }
        return points;
    }

    /**
     * Определяет положение точки относительно эллипса.
     * @return 0 — точка на эллипсе, 1 — внутри, 2 — снаружи.
     */
    private static int classifyPoints(double h, double k, double a, double b, double x, double y) {
        // Уравнение эллипса: (x - h)²/a² + (y - k)²/b² = 1
        double value = Math.pow((x - h) / a, 2) + Math.pow((y - k) / b, 2);
        if (value == 1) {
            return 0;
        } else if (value < 1) {
            return 1;
        } else {
            return 2;
        }
    }
}
