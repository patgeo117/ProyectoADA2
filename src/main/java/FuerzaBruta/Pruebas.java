package FuerzaBruta;

import java.util.Arrays;
import java.util.Random;

public class Pruebas {
    public static void main(String[] args) {
        int n = 6;
        int[][] calendario = generarCalendario(n);
        imprimirCalendario(calendario);
    }

    public static int[][] generarCalendario(int n) {
        int[][] mitadCalendario = generarMitadCalendario(n);
        return combinarCalendario(n, mitadCalendario);
    }

    public static int[][] combinarCalendario(int n, int[][] mitadCalendario) {
        int[][] calendario = new int[2 * (n - 1)][n];

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n; j++) {
                calendario[i][j] = mitadCalendario[i][j];
                calendario[i + n - 1][j] = -mitadCalendario[i][j];
            }
        }

        return calendario;
    }

    private static final int MAX_INTENTOS = 200000000;

    public static int[][] generarMitadCalendario(int n) {
        int[][] mitadCalendario = new int[(n - 1)][n];
        int intentos = 0;
        boolean generado = false;

        while (!generado && (intentos < MAX_INTENTOS)) {
            for (int i = 0; i < n - 1; i++) {
                int[] permutacion = generarPermutacion(n);
                mitadCalendario[i] = permutacion;
            }
            intentos++;

            if (checkNoDuplicatesColumns(mitadCalendario) && checkNoValuesInColumns(mitadCalendario)) {
                generado = true;
            }
        }

        if (intentos >= MAX_INTENTOS) {
            System.out.println("Se alcanzó el límite de intentos.");
            // Reiniciar la matriz y el contador de intentos para un nuevo intento.
            mitadCalendario = new int[(n - 1)][n];
            intentos = 0;
            generarCalendario(n);
        }

        return mitadCalendario;
    }

    private static boolean checkNoDuplicatesColumns(int[][] matrix) {
        int filas = matrix.length;
        int columnas = matrix[0].length;

        for (int j = 0; j < columnas; j++) {
            for (int i = 0; i < filas - 1; i++) {
                for (int k = i + 1; k < filas; k++) {
                    if (matrix[i][j] == matrix[k][j]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean checkNoValuesInColumns(int[][] matrix) {
        int filas = matrix.length;
        int columnas = matrix[0].length;

        for (int j = 0; j < columnas; j++) {
            for (int i = 0; i < filas; i++) {
                if (matrix[i][j] == j + 1) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[] generarPermutacion(int n) {
        int[] permutation = new int[n];

        for (int i = 0; i < n; i++) {
            permutation[i] = i + 1;
        }
        Random random = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = permutation[i];
            if (permutation[i] != permutation[j]) {
                permutation[i] = permutation[j];
                permutation[j] = temp;
            }
        }
        return permutation;
    }

    public static void imprimirCalendario(int[][] calendario) {
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }
}

