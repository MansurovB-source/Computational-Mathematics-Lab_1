import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

class GaussSeidel {

    private double[][] global_k;
    private double[] global_d;
    private double[] prev_x;
    private double[] x;
    private double[] x_prev;
    private double min = 1000;
    private ArrayList<Double> eps = new ArrayList<>();
    private double epsilon = 0.01;
    private int iteration;
    private boolean diag = true;
    //StringBuilder stringBuilder;

    private void showMatrix() {
        for (int i = 0; i < global_k.length; i++) {
            for (int j = 0; j < global_k.length; j++) {
                System.out.print(" | " + global_k[i][j] + " ");
            }
            System.out.println(" | " + global_d[i]);
        }
        System.out.println("---------------------------------------------------------");
    }

    private void iterate() {
        iteration++;
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x.length; j++) {
                if (i == j) {
                    continue;
                }
                x[i] += global_k[i][j] * prev_x[j];
            }
            x[i] += global_d[i];
            prev_x[i] = x[i];
            x[i] = 0;
        }
    }

    private boolean checkEpsilon() {
        if (iteration < 1) {
            return false;
        }
        boolean b = true;
        for (int i = 0; i < x.length; i++) {
            min = Math.min(abs(prev_x[i] - x_prev[i]), min);
            if (abs(prev_x[i] - x_prev[i]) <= epsilon) {
                b = b && true;
            } else {
                b = b && false;
            }
            x_prev[i] = prev_x[i];
        }
        eps.add(min);
        min = 1000;
        return b;
    }

    private boolean checkDiagonal() {
        boolean b = true;
        double sum = 0;
        for (int i = 0; i < global_k.length; i++) {
            for (int j = 0; j < global_k[i].length; j++) {
                sum += abs(global_k[i][j]);
            }

            if (sum < 1) {
                b = b && true;
            } else {
                b = b && false;
            }
            sum = 0;
        }
        return b;
    }

    private void cutK() {
        for (int i = 0; i < global_k.length; i++) {
            for (int j = 0; j < global_k[i].length; j++) {
                if (i == j) {
                    continue;
                }
                global_k[i][j] = -1 * global_k[i][j] / global_k[i][i];
//                System.out.println(global_k[i][i] + " " + global_k[i][j]);
            }
            global_d[i] = global_d[i] / global_k[i][i];
            global_k[i][i] = 0;
        }

        prev_x = Arrays.copyOf(global_d, global_d.length);
        x_prev = Arrays.copyOf(global_d, global_d.length);
        x = new double[global_k.length];
        Arrays.fill(x, 0);
    }

    private void makeDiagonal(double[][] k, double[] d) {
        int length = k.length;
        int i;
        int j;
        boolean[][] check = new boolean[length][length];
        for (i = 0; i < length; i++) {
            double summ = 0;
            for (j = 0; j < length; j++) {
                summ += abs(k[i][j]);
            }
            for (j = 0; j < length; j++) {
                check[i][j] = summ < 2 * abs(k[i][j]);
            }
        }

        this.global_k = new double[check.length][check.length];
        this.global_d = new double[check.length];

        for (i = 0; i < check.length; i++) {
            boolean b = false;
            for (j = 0; j < check.length; j++) {
                if (check[j][i]) {
                    b = true;
                    global_k[i] = Arrays.copyOf(k[j], k[j].length);
                    global_d[i] = d[j];
                    break;
                }
            }
            if (!b) {
                diag = false;
                System.out.println("Нет диагонального преобладания для " + (i + 1) + " - ой переменной");
                //System.out.println("Результат будет не точным или не будет результата");
            }
        }
    }


    void solve(double[][] k_k, double[] d_k, double e) {
        System.out.println("---------------------------------------------------------");
        makeDiagonal(k_k, d_k);
        if (diag) {
            System.out.println("Матрица после диагонального преобладания");
            showMatrix();
            System.out.println("Приведем ситстему к виду x = Cx + d");
            showMatrix();
            cutK();
            this.epsilon = e;
            if (checkDiagonal()) {
                while (!checkEpsilon()) {
                    iterate();
                }
                System.out.println("Количество итераций " + (iteration + 1));
                for (int i = 0; i < x_prev.length; i++) {
                    System.out.println(" | x_" + (i + 1) + " = " + String.format("%.5f", x_prev[i]) + " ");
                }
            }
            System.out.println("---------------------------------------------------------");
            for (int i = 0; i < iteration; i++) {
                System.out.println("| Погрешность на " + (i + 2) + "-ой итерации" + "   max e" + (i + 2) + " = " + String.format("%.5f", eps.get(i)));
            }
        } else {
            System.out.println("Так как нету диагонального преобладания то система не имеет решение");
        }
    }
}
