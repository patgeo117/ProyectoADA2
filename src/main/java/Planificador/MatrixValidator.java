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
    public boolean checkNoDuplicatesValue(int[][] matrix) {
        int n = matrix[0].length;

        for (int j = 0; j < n; j++) {
            Set<Integer> valuesColumn = new HashSet<>(matrix.length);

            for (int[] ints : matrix) {
                int absoluteValue = ints[j];

                if (absoluteValue < 0) {
                    absoluteValue = -absoluteValue;
                }

                if (!valuesColumn.add(absoluteValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Evaluar los minimos y maximos.
     * Nota: Permanencia --> Partidos locales, Gira --> Partidos visitantes
     * @param max Maximo paridos consecutivos
     * @param calDep Matriz de calendario de partidos
     */
    public  boolean MaxGame(int max, int min, int[][] calDep) {
        for (int j = 0; j < calDep[0].length; j++) {
            int consecutivosPositivos = 0;
            int consecutivosNegativos = 0;

            for (int[] ints : calDep) {
                if (ints[j] > 0) {
                    consecutivosPositivos++;
                    consecutivosNegativos = 0; // Reiniciar el contador de negativos
                    if (consecutivosPositivos > max) {
                        return false;
                    }
                } else if (ints[j] < 0) {
                    consecutivosNegativos++;
                    consecutivosPositivos = 0; // Reiniciar el contador de positivos
                    if (consecutivosNegativos > max) {
                        return false;
                    }
                } else {
                    consecutivosPositivos = 0; // Reiniciar ambos contadores si encuentra un valor positivo o cero
                    consecutivosNegativos = 0;
                }
            }
        }
        return true;
    }
}

