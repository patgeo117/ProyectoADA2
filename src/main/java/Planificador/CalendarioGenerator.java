package Planificador;

import SeleccionarArchivos.SeleccionadorArchivos;

import java.util.*;

/**
 * Esta clase proporciona funcionalidad para generar un calendario de partidos de fútbol.
 *
 */
public class CalendarioGenerator {
    PermutacionGenerator permutacionGenerator = new PermutacionGenerator();
    MatrixValidator matrixValidator = new MatrixValidator();

    public static void main(String[] args) {
        CalendarioGenerator calendarioGenerator = new CalendarioGenerator();
        int n = 8;
        int min = 1;
        int max = 8;
        try {
            validarNumeroEquipos(n);
            int[][] calendario = calendarioGenerator.generarCalendario(n, max, min);
            imprimirCalendario(calendario);

            // Resto del código
        } catch (NumeroEquiposImparException e) {
            System.out.println("Error: El número de equipos debe ser par.");
        }

    }

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
    /**
     *  Imprime el calendario de partidos en la consola.
     *
     * @param calendario La matriz que representa el calendario de partidos.
     */
    public static void imprimirCalendario(int[][] calendario) {
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }

    /**
     * Valida que el número de equipos sea par. En caso contrario, lanza una excepción.
     *
     * @param n El número de equipos.
     */
    private  static void validarNumeroEquipos(int n) {
        if (n % 2 != 0) {
            throw new SeleccionadorArchivos.NumeroEquiposImparException("El número de equipos debe ser par.");
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

