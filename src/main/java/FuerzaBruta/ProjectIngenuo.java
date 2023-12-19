package FuerzaBruta;

import java.util.*;

/**
 * Esta clase representa un programa que genera un calendario de partidos de fútbol de ida y vuelta
 * para un número par de equipos. El calendario generado no tiene partidos repetidos ni equipos que jueguen
 * contra sí mismos.
 */
public class ProjectIngenuo {

    /**
     * Método principal que inicia la ejecución del programa.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        int n = 6;

        try {
            validarNumeroEquipos(n);
            int[][] calendario = generarCalendario(n);
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
    public static int[][] combinarCalendario(int n, int[][] mitadCalendario) {
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
    private static int[][] generarMitadCalendario(int n) {
        int MAX_INTENTOS = 1000000000;
        int[][] mitadCalendario = new int[(n - 1)][n];

        int intentos = 0;

        while (intentos < MAX_INTENTOS) {
            for (int i = 0; i < n - 1; i++) {
                mitadCalendario[i] = generarPermutacion(n);
            }
            intentos++;

            if (checkNoDuplicatesValue(mitadCalendario) && checkNoValuesInColumns(mitadCalendario)) {
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
     * Verifica que no haya valores duplicados en las columnas de la matriz.
     *
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
     * Verifica que los valores no coincidan con la columna.
     *
     * @param matrix La matriz a verificar.
     * @return true si no hay duplicados, false en caso contrario.
     */
    private static boolean checkNoValuesInColumns(int[][] matrix) {
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
        int maxIntentos = factorial(n);

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

        return checkGameEquipos(check);
    }

    private static boolean esPermutacionValida(List<Integer> permutation) {
        for (int i = 0; i < permutation.size(); i++) {
            if (permutation.get(i) == i + 1) {
                return false;
            }
        }
        return true;
    }

    private static int[] checkGameEquipos(int[] permutaciones){
        boolean aux = true;
        for(int i: permutaciones){
            for(int j = 0; j<permutaciones.length;j++){
                if(i == permutaciones[j]){
                    permutaciones[i-1] = j+1;
                }
            }
        }
        return permutaciones;
    }
    /**
     * Calcula el factorial de un número.
     *
     * @param n El número para calcular el factorial.
     * @return El factorial de n.
     */
    private static int factorial(int n) {
        int factorial = 1;
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }

        return factorial;
    }

    /**
     * Imprime el calendario de partidos en la consola.
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
    private static void validarNumeroEquipos(int n) {
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


