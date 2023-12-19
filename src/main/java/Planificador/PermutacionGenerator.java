package Planificador;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.IntStream;

/**
 * Esta clase proporciona métodos para generar permutaciones aleatorias de números enteros del 1 al n.
 */
public class PermutacionGenerator {

    /**
     * Genera una permutación aleatoria de números del 1 al n.
     *
     * @param n El número de elementos en la permutación.
     * @return La permutación generada.
     */
    public static int[] generarPermutacion(int n) {
        List<Integer> permutationList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            permutationList.add(i);
        }

        int intentos = 0;
        int maxIntentos = FactorialCalculator.factorial(n);

        int[] check = new int[n];

        do {
            Collections.shuffle(permutationList);
            for (int i = 0; i < n; i++) {
                check[i] = permutationList.get(i);
            }
            intentos++;
        } while (!esPermutacionValida(permutationList) && intentos < maxIntentos);

        if (intentos >= maxIntentos) {
            System.out.println("Se alcanzó el límite de intentos para generar permutaciones válidas.");
            return new int[n]; // O lanzar una excepción según tus necesidades.
        }

        return checkGameTeam(check);
    }

    /**
     * Verifica si una permutación es válida, es decir, si ningún elemento está en su posición original.
     *
     * @param permutation La permutación a verificar.
     * @return true si la permutación es válida, false en caso contrario.
     */
    private static boolean esPermutacionValida(List<Integer> permutation) {
        for (int i = 0; i < permutation.size(); i++) {
            if (permutation.get(i) == i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Genera una permutación aleatoria de números del 1 al n para asegurar que haya la mitad de equipos positivos y
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

        IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] > 0)
                .forEach(i -> permutaciones[permutaciones[i] - 1] = -(i + 1));

        return permutaciones;
    }
}
