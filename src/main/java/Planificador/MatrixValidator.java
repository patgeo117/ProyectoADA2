package Planificador;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que proporciona métodos para validar matrices de enteros.
 */
public class MatrixValidator {

    /**
     * Verifica que no haya valores duplicados en las columnas de la matriz.
     *
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    public static boolean checkNoDuplicatesValue(int[][] matrix) {
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
     * Verifica que los valores no coincidan con la columna.
     *
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    public static boolean checkNoValuesInColumns(int[][] matrix) {
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
}

