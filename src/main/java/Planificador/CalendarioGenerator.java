package Planificador;

import java.util.*;

/**
 * Esta clase proporciona funcionalidad para generar un calendario de partidos de fútbol.
 *
 */
public class CalendarioGenerator {
    PermutacionGenerator permutacionGenerator = new PermutacionGenerator();
    MatrixValidator matrixValidator = new MatrixValidator();

    /**
     * Genera un calendario de partidos de fútbol de ida y vuelta para un número par de equipos.
     *
     * @param n El número de equipos.
     * @return Una matriz de n x 2(n-1) con los partidos de fútbol de ida y vuelta.
     */
    public int[][] generarCalendario(int n, int max, int min) {
        int[][] mitadCalendario = generarMitadCalendario(n, max, min);
        return combinarCalendario(n, mitadCalendario);
    }

    /**
     * Combina la mitad del calendario generada para obtener los partidos de ida y vuelta.
     *
     * @param n              El número de equipos.
     * @param mitadCalendario La mitad del calendario generada.
     * @return Una matriz con los partidos de fútbol de ida y vuelta.
     */
    public int[][] combinarCalendario(int n, int[][] mitadCalendario) {
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
     * Genera la mitad del calendario aleatorio para un número par de equipos.
     *
     * @param n El número de equipos.
     * @return La mitad del calendario generada.
     */
    public int[][] generarMitadCalendario(int n, int max, int min) {
        int MAX_INTENTOS = 100000000;
        int[][] mitadCalendario = new int[(n - 1)][n];

        int intentos = 0;

        while (intentos < MAX_INTENTOS) {
            for (int i = 0; i < n - 1; i++) {
                mitadCalendario[i] = permutacionGenerator.generarPermutacion(n);;
            }
            intentos++;

            if (matrixValidator.checkNoDuplicatesValue(mitadCalendario) && matrixValidator.MaxGame(max, min,mitadCalendario)) {
                for (int[] i : mitadCalendario) {
                    System.out.println(Arrays.toString(i));
                }
                System.out.println("--------------------");

                return mitadCalendario;
            }
        }

        throw new RuntimeException("Se alcanzó el límite de intentos sin generar un calendario válido.");
    }
}

