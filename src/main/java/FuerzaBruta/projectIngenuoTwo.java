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
        //mitad superior
        for (int i = 0; i < n-1; i++) {
            int[] permutacion = generarPermutacion(n);
            calendario[i] = permutacion;
            for(int j = 0; j < n; j++){
                calendario[i+n-1][j] = -permutacion[j];
            }
        }

        // Verificar restricciones adicionales
        while (!checkNoDuplicatesValue(calendario)){
            calendario = generarCalendario(n); // Regenerar si no cumple con las restricciones
        }

        return calendario;
    }

    // Método para generar una permutación aleatoria de los números del 1 al n
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
    /**
     * Verifica que no haya valores duplicados en las columnas de la matriz.
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoDuplicatesValue(int[][] matrix) {
        int[] column = new int[matrix.length];
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                if (column.equals(matrix[i][j])) {
                    generarPermutacion(matrix[0].length);
                }
            }

            if (!checkNoDuplicatesArray(column) || !checkColumValor(matrix)) {
                return false;
            }
        }
        return true;
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
