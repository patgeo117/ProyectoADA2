package FuerzaBruta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class projectIngenuoTwo {
    public static void main(String[] args) {
        int n = 4; // Puedes ajustar el valor de n según tus necesidades
        int[][] calendario = generarCalendario(n);

        // Imprimir el calendario generado
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }

    private static final int MAX_INTENTOS = 200000000;
    private static int[][] generarCalendario(int n) {
        int[][] calendario;
        int intentos = 0;

        do {
            calendario = generarCalendarioSinVerificacion(n);
            intentos++;
        } while ((intentos < MAX_INTENTOS) && (!checkNoDuplicatesValue(calendario) || !checkColumValor(calendario)));

        if (intentos >= MAX_INTENTOS) {
            System.out.println("Se alcanzó el límite de intentos y no se pudo generar un calendario válido.");
        }

        return calendario;
    }
    private static int[][] generarCalendarioSinVerificacion(int n) {
        int[][] calendario = new int[2 * (n - 1)][n];
        //mitad superior
        for (int i = 0; i < n-1; i++) {
            int[] permutacion = generarPermutacion(n);
            calendario[i] = permutacion;
            // mitad inferior
            for (int j = 0; j < n; j++) {
                calendario[i + n - 1][j] = -permutacion[j];
            }
        }

        return calendario;
    }

    // Método para generar una permutación aleatoria de los números del 1 al n
    private static int[] generarPermutacion(int n) {
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
