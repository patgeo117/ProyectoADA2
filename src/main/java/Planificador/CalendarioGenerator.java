package Planificador;

import java.util.*;

/**
 * Esta clase proporciona funcionalidad para generar un calendario de partidos de fútbol.
 *
 */
public class CalendarioGenerator {

    /**
     * Genera un calendario de partidos de fútbol de ida y vuelta para un número par de equipos.
     *
     * @param n El número de equipos.
     * @return Una matriz de n x 2(n-1) con los partidos de fútbol de ida y vuelta.
     */
    public static int[][] generarCalendario(int n) {
        int[][] mitadCalendario = generarMitadCalendario(n);
        return combinarCalendario(n, mitadCalendario);
    }

    /**
     * Combina la mitad del calendario generada para obtener los partidos de ida y vuelta.
     *
     * @param n              El número de equipos.
     * @param mitadCalendario La mitad del calendario generada.
     * @return Una matriz con los partidos de fútbol de ida y vuelta.
     */
    private static int[][] combinarCalendario(int n, int[][] mitadCalendario) {
        int[][] calendario = new int[2 * (n - 1)][n];

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n; j++) {
                calendario[i][j] = mitadCalendario[i][j];
                calendario[i + n - 1][j] = -mitadCalendario[i][j];
            }
        }
        return calendario;
    }

    /**
     * El número máximo de intentos para generar un calendario único.
     */
    private static final int MAX_INTENTOS = 1000000000;

    /**
     * Genera la mitad del calendario aleatorio para un número par de equipos.
     *
     * @param n El número de equipos.
     * @return La mitad del calendario generada.
     * @throws RuntimeException Si no se puede generar un calendario único
     *         dentro del límite de intentos.
     */
    public static int[][] generarMitadCalendario(int n) {
        int[][] mitadCalendario = new int[n - 1][n];

        int intentos = 0;

        while (intentos < MAX_INTENTOS) {
            generarPermutacionesUnicas(n, mitadCalendario);
            intentos++;

            // Verifica si la matriz generada es válida
            if (MatrixValidator.checkNoDuplicatesValue(mitadCalendario) &&
                    MatrixValidator.checkNoValuesInColumns(mitadCalendario)) {
                // Imprime la matriz y devuelve el resultado
                for (int[] i : mitadCalendario) {
                    System.out.println(Arrays.toString(i));
                }
                System.out.println("--------------------");
                return mitadCalendario;
            }
        }

        // Lanza una excepción si no se puede generar un calendario válido
        throw new RuntimeException("Se alcanzó el límite de intentos sin generar un calendario válido.");
    }

    /**
     * Genera permutaciones únicas y las asigna a la mitad del calendario.
     *
     * @param n El número de equipos.
     * @param mitadCalendario La mitad del calendario a llenar con permutaciones únicas.
     */
    private static void generarPermutacionesUnicas(int n, int[][] mitadCalendario) {
        for (int i = 0; i < n - 1; i++) {
            int[] permutacion = PermutacionGenerator.generarPermutacion(n);
            while (!esPermutacionUnica(permutacion, mitadCalendario, i)) {
                permutacion = PermutacionGenerator.generarPermutacion(n);
            }
            mitadCalendario[i] = permutacion;
        }
    }

    /**
     * Verifica si una permutación es única en la mitad del calendario.
     *
     * @param permutacion La permutación a verificar.
     * @param mitadCalendario La mitad del calendario actual.
     * @param index El índice actual en la mitad del calendario.
     * @return true si la permutación es única, false de lo contrario.
     */
    private static boolean esPermutacionUnica(int[] permutacion, int[][] mitadCalendario, int index) {
        for (int i = 0; i < index; i++) {
            if (Arrays.equals(permutacion, mitadCalendario[i])) {
                return false; // Ya existe esta permutación
            }
        }
        return true;
    }

    /**
     * Valida que el número de equipos sea par. En caso contrario, lanza una excepción.
     *
     * @param n El número de equipos.
     * @throws NumeroEquiposImparException Si el número de equipos es impar.
     */
    public static void validarNumeroEquipos(int n) throws NumeroEquiposImparException {
        if (n % 2 != 0) {
            throw new NumeroEquiposImparException("El número de equipos debe ser par.");
        }
    }

    /**
     * Excepción personalizada para indicar que el número de equipos es impar.
     */
    public static class NumeroEquiposImparException extends RuntimeException {
        /**
         * Construye una nueva instancia de la excepción con el mensaje especificado.
         *
         * @param message El mensaje de la excepción.
         */
        public NumeroEquiposImparException(String message) {
            super(message);
        }
    }
}

