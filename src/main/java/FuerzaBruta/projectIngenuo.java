package FuerzaBruta;

import java.util.Arrays;
import java.util.Random;

public class projectIngenuo {
    /**
     * Genera una matriz de tamaño 2(n-1) x n que contiene una permutación de los números del 1 al n en cada fila.
     * @param n El número de equipos.
     * @return Una matriz de tamaño 2(n-1) x n que contiene una permutación de los números del 1 al n en cada fila.
     */
    public static int[][] generateArray(int n) {
        if (n % 2 != 0) {
            throw new IllegalArgumentException("El valor de n debe ser par.");
        }

        int[][] matriz = new int[2 * (n - 1)][n];

        int intentosMaximos = 20000;
        int intentos = 0;

        while (!checkNoDuplicates(matriz) && intentos < intentosMaximos) {
            // generar matriz superior
            for (int i = 0; i < (n / 2) + 1; i++) {
                matriz[i] = generateRandomPermutation(n);
            }

            for (int i = 1 + (n / 2); i < 2 * (n - 1); i++) {
                matriz[i] = Arrays.copyOf(generateInverse(matriz[i - (n / 2) - 1]), n);
            }
            intentos++;
        }

        if (intentos >= intentosMaximos) {
            System.out.println("No se pudo generar una matriz válida después de " + intentosMaximos + " intentos.");
            // Puedes decidir qué hacer en este caso, como lanzar una excepción.
        }
        return matriz;
    }
    /**
     * Genera una permutación aleatoria de los números del 1 al n.
     * @param n El número de equipos.
     * @return Una permutación aleatoria de los números del 1 al n.
     */
    private static int[] generateRandomPermutation(int n) {
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
     * Genera la inversa de las permutaciones de la matriz superior .
     * @param array La permutación a invertir.
     * @return La inversa de la permutación.
     */
    private static int[] generateInverse(int[] array) {
        int[] inverse = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            inverse[i] = -array[i];
        }

        return inverse;
    }
    /**
     * Verifica que no haya duplicados en la matriz.
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoDuplicates(int[][] matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
            int[] column = new int[matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                column[i] = matrix[i][j];
            }

            if (!checkNoDuplicates(column) || !checkColumValor(matrix)) {
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
    private static boolean checkNoDuplicates(int[] array) {
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
     * Verificar que no los valores no coincidan con la columna .
     * @param matriz El arreglo a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkColumValor(int[][] matriz) {
        for (int j = 0; j < matriz[0].length; j++) {
            for (int i = 0; i < matriz.length; i++) {
                if (matriz[i][j] == j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Imprime una matriz en la consola.
     * @param matrix La matriz a imprimir.
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
    /**
     * Método principal.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        int nValue = 4;
        int[][] generatedArray = generateArray(nValue);
        printMatrix(generatedArray);
    }
}

