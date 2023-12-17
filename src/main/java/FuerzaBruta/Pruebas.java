package FuerzaBruta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Pruebas {

    public static void main(String[] args) {
        int n = 8;
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

    private static int[][] generarMitadCalendario(int n) {
       int MAX_INTENTOS = 100000000;// 20000000

        int[][] mitadCalendario = new int[(n - 1)][n];
        int intentos = 0;
        boolean generado = false;

        while (!generado && (intentos < MAX_INTENTOS)) {
            for (int i = 0; i < n - 1; i++) {
                int[] permutacion = generarPermutacion(n);
                mitadCalendario[i] = permutacion;
            }
            intentos++;

            if (checkNoDuplicatesValue(mitadCalendario) && checkNoValuesInColumns(mitadCalendario)) {
                generado = true;
            }
        }

        if (intentos >= MAX_INTENTOS) {
            System.out.println("Se alcanzó el límite de intentos.");
            // Reiniciar la matriz y el contador de intentos para un nuevo intento.
            mitadCalendario = new int[(n - 1)][n];
            generarCalendario(n);
        }

        return mitadCalendario;
    }
    /**
     * Verifica que no haya valores duplicados en las columnas de la matriz.
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoDuplicatesValue(int[][] matrix) {
        int n = matrix[0].length;

        for (int j = 0; j < n; j++) {
            Set<Integer> valuesColumn = new HashSet<>();
            for (int[] ints : matrix) {
                if (!valuesColumn.add(ints[j])) {
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Verificar que los valores no coincidan con la columna .
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoValuesInColumns(int[][] matrix) {
        //int filas = matrix.length;
        int columnas = matrix[0].length;

        for (int j = 0; j < columnas; j++) {
            for (int[] ints : matrix) {
                if (ints[j] == j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] generarPermutacion(int n) {
        int MAX_INTENTOS_PERMUTACION = factorial(n);

        int[] permutation = new int[n];
        boolean permutacionValida = false;
        int intentos = 0;

        while (!permutacionValida && (intentos < MAX_INTENTOS_PERMUTACION)) {
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

            // Verificar la validez de la permutación
            if (!checkNoValuesInColumns(new int[][]{permutation})) {
                intentos++;
            } else {
                permutacionValida = true;
            }
        }

        if (intentos >= MAX_INTENTOS_PERMUTACION) {
            System.out.println("Se alcanzó el límite de intentos para generar permutaciones válidas.");
            // Puedes manejar esto según tus necesidades, por ejemplo, lanzar una excepción o retornar un valor predeterminado.
            return new int[n];
        }

        return permutation;
    }

    private static int factorial(int n) {
        int factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }

        return factorial;
    }

    public static void imprimirCalendario(int[][] calendario) {
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }
}

