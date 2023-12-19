package Planificador;

import java.util.Arrays;


/**
 * Clase principal que contiene el método main para generar e imprimir un calendario de partidos de fútbol.
 */
public class Main {

    /**
     * Método principal que inicia la ejecución del programa.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        int n = 10;

        try {
            // Validar que el número de equipos sea par
            CalendarioGenerator.validarNumeroEquipos(n);

            // Generar el calendario de partidos
            int[][] calendario = CalendarioGenerator.generarCalendario(n);

            // Imprimir el calendario de partidos
            imprimirCalendario(calendario);

            // Resto del código
        } catch (CalendarioGenerator.NumeroEquiposImparException e) {
            System.out.println("Error: El número de equipos debe ser par.");
        }
    }

    /**
     * Imprime el calendario de partidos en la consola.
     *
     * @param calendario La matriz que representa el calendario de partidos.
     */
    private static void imprimirCalendario(int[][] calendario) {
        for (int[] i : calendario) {
            System.out.println(Arrays.toString(i));
        }
    }
}

