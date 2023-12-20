package Planificador;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Esta clase proporciona métodos para generar permutaciones aleatorias de números enteros del 1 al n.
 */
public class PermutacionGenerator {

    FactorialCalculator factorialCalculator = new FactorialCalculator();

    /**
     * Semilla para el generador de números aleatorios.
     */
    private static final int SEED = 42; // Puedes cambiar la semilla según tus necesidades
    private static final Random rand = new Random(SEED);

    /**
     * Verifica si una permutación es válida, es decir, si ningún elemento está en su posición original.
     * @param permutation La permutación a verificar.
     * @return true si la permutación es válida, false en caso contrario.
     */
    public boolean esPermutacionValida(int[] permutation) {
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] == i + 1) {
                return false;
            }
        }
        return true;
    }
    /**
     * Genera una permutación aleatoria de números del 1 al n.
     * @param n El número de elementos en la permutación.
     * @return La permutación generada.
     */
    public int[] generarPermutacion(int n){
        int[] permutation = IntStream.range(1, n + 1).toArray();

        int intentos = 0;
        int maxIntentos = factorialCalculator.factorial(n);

        do {
            shuffleArray(permutation);
            intentos++;
        } while (!esPermutacionValida(permutation) && (intentos < maxIntentos));

        if (intentos >= maxIntentos) {
            System.out.println("Se alcanzó el límite de intentos para generar permutaciones válidas.");
            return new int[n]; // O lanzar una excepción según tus necesidades.
        }

        return checkGameTeam(permutation);
    }

    /**
     * Implementación del algoritmo de Fisher-Yates para permutar un arreglo.
     * @param array El arreglo a permutar.
     */
    public   void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Genera una permutación aleatoria de números del 1 a n para asegurar que haya la mitad de equipos positivos y
     * la mitad negativos en cada fila.
     * @param permutaciones El número de elementos en la permutación.
     * @return La permutación generada.
     */
    private static int[] checkGameTeam(int[] permutaciones){
        // Verificar que cada equipo se enfrente con un oponente y ese oponente se enfrente con el equipo.
        IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] > 0)
                .forEach(i -> permutaciones[permutaciones[i] - 1] = (i + 1));

        // Asinar el signo negativo a la mitad de los equipos.
        IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] > 0)
                .forEach(i -> permutaciones[permutaciones[i] - 1] = -(i + 1));

        return permutaciones;
    }
}
