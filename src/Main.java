import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static boolean flag = false;
    private static double[][] k = null;
    private static double[] d = null;
    private static double epsilon = 0;

    private static String[] readFromFile(String file) {
        flag = true;
        String[] strings = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            epsilon = Double.parseDouble(br.readLine());
            int length = Integer.parseInt(br.readLine());
            k = new double[length][length];
            d = new double[length];
            strings = new String[length];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = String.valueOf(br.readLine());
            }
        } catch (IOException e) {
            flag = false;
            System.out.println("Не удалось открыт файл\nФайл поврежден или находится в другом месте\nИли некорректные данные\n");
        }
        return strings;
    }

    public static void main(String[] args) {
        String[] strings;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Хотите задать случайные аргументы?");
        System.out.println("( Введите ' Да ' ) или ' Нет ' ");
        String random;
        random = scanner.nextLine();
        String file;
        random = random.toLowerCase();
        if (random.equals("да")) {
            System.out.println("Аргументы будут заданы случайным образом");
            k = new double[][]{{20, 2, 3, 7}, {1, 12, -2, -5}, {5, -3, 13, 0}, {0, 0, -1, 15}};
            d = new double[]{5, 4, -3, 7};
            epsilon = 0.01;
        }

        if (random.equals("нет")) {
            //flag = false;
            System.out.println("Хотите задать данные через файл?");
            System.out.println("( Введите ' Да ' ) или ' Нет ' ");
            String str = scanner.nextLine();
            str = str.toLowerCase();
            if (str.equals("да")) {
                System.out.println("Введите имя файла");
                file = scanner.nextLine();
                strings = readFromFile(file);
                if (flag) {
                    for (int i = 0; i < strings.length; i++) {
                        String s[] = strings[i].split(" ");
                        for (int j = 0; j < s.length; j++) {
                            if (j == (s.length - 1)) {
                                d[i] = Double.parseDouble(s[j]);
//                                System.out.println(d[i]);
                                break;
                            }
                            k[i][j] = Double.parseDouble(s[j]);
//                            System.out.println(k[i][j]);
                        }
                    }
                    System.out.println(epsilon);
                }
            }
            if (str.equals("нет")) {
                System.out.println("Задайте точность;");
                epsilon = scanner.nextDouble();
                System.out.println("Задайте размер матрицы");
                int length = scanner.nextInt();
                k = new double[length][length];
                d = new double[length];
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < length + 1; j++) {
                        if (j == length) {
                            System.out.println("Введите" + "[" + (i + 1) + "]" + "свободдный член");
                            d[i] = scanner.nextDouble();
                            break;
                        }
                        System.out.println("Введите" + "[" + (i + 1) + "][" + (j + 1) + "]" + "элемент");
                        k[i][j] = scanner.nextDouble();
                    }
                }
            }
        }


        GaussSeidel gaussSeidel = new GaussSeidel();
        System.out.println("--------------------------------------------------------");
        System.out.println("Текущая матрица");
        for (int i = 0; i < k.length; i++) {
            for (int j = 0; j < k.length; j++) {
                System.out.print(k[i][j] + " ");
            }
            System.out.println(d[i]);
        }
        gaussSeidel.solve(k, d, epsilon);
//        System.out.println("---------------------------------------------------------");
//        gaussSeidel.makeDiagonal(k, d);
//        System.out.println("Матрица после диагонального преобладания");
//        gaussSeidel.showMatrix();
//        System.out.println("Приведем ситстему к виду x = Cx + d");
//        gaussSeidel.showMatrix();
//        gaussSeidel.cutK();
//
//        boolean check = gaussSeidel.checkDiagonal();
//        gaussSeidel.solve(check, epsilon)
    }
}
