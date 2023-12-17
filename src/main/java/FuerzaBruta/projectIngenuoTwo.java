package FuerzaBruta;

import java.util.Arrays;
import java.util.Random;

public class projectIngenuoTwo {
    public static void main(String[] args) {
        int n =6; // Puedes ajustar el valor de n según tus necesidades
        int[][] calendario = generarCalendario(n);

        // Imprimir el calendario generado
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }

    public static int[][] generarCalendario(int n) {
        int[][] calendario = new int[2 * (n - 1)][n];

        for (int i = 0; i < n - 1; i++) {
            int[] permutacion = generarPermutacionSinDuplicados(n, calendario);
            calendario[i] = permutacion;

            // Generar la permutación opuesta (negativos)
            for (int j = 0; j < n; j++) {
                calendario[i + n - 1][j] = -permutacion[j];
            }
        }

        // Verificar restricciones adicionales
        while (!checkNoDuplicatesValue(calendario)) {
            calendario = generarCalendario(n); // Regenerar si no cumple con las restricciones
        }

        return calendario;
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
            permutation[i] = permutation[j];
            permutation[j] = temp;
        }

        return permutation;
    }

    public static int[] generarPermutacionSinDuplicados(int n, int[][] matrix) {
        int[] permutation = generarPermutacion(n);

        // Verificar duplicados y regenerar si es necesario
        while (tieneDuplicados(permutation, matrix)) {
            permutation = generarPermutacion(n);
        }

        return permutation;
    }

    private static boolean checkNoDuplicatesValue(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            int[] column = new int[matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                column[i] = matrix[i][j];
            }

            // Imprimir los valores de la columna después de llenarla
            System.out.print("Columna " + j + ": ");
            for (int i = 0; i < column.length; i++) {
                System.out.print(column[i] + " ");
            }
            System.out.println();

            // Verificar si hay duplicados en la columna
            if (tieneDuplicados(column, matrix)) {
                System.out.println("La columna " + j + " tiene duplicados.");
                return false;
            }
        }

        return true;
    }

    public static boolean tieneDuplicados(int[] column, int[][] matrix) {
        for (int i = 0; i < column.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] == column[i]) {
                    return true;  // Se encontró un duplicado en la columna
                }
            }
        }
        return false;  // No se encontraron duplicados
    }


    /**
     * Verifica que no haya duplicados en el arreglo.
     * @param array El arreglo a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoDuplicatesArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
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
    public static boolean checkColumValor(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            for (int[] ints : matrix) {
                if (ints[j] == j + 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
