package FuerzaBruta;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Esta clase representa un programa que genera un calendario de partidos de fútbol de ida y vuelta
 * para un número par de equipos. El calendario generado no tiene partidos repetidos ni equipos que jueguen
 * contra sí mismos.
 */
public class ProjectIngenuo {

    int min = 1; // numero minimo de permanencia consecutiva en la gira
    int max = 3; // número maximo de permanencia consecutiva en la gira
    int[][] matrizCostos = {
            {0, 745, 665, 929},
            {745, 0, 80, 337},
            {665, 80, 0, 380},
            {929, 337, 380, 0}
    };

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
     /*   int aux = 1;
        while (aux != 0) {
            if (MaxMin(3, 1, calendario)) {
                System.out.println("--------BIEN------------");
                aux = 0;
            } else {
                // Generar número aleatorio entre 1 y la mitad de la longitud de la matriz
                Random rand = new Random();
                int randomNumber = rand.nextInt(calendario.length / 2) + 1;

                // Intercambiar el número aleatorio con la posición (calendario.length / 2)
                intercambiarNumeros(calendario, calendario.length / 2, randomNumber);
            }
        }*/

        return calendario;
    }

    // Función para intercambiar dos números en una matriz
    private static void intercambiarNumeros(int[][] matriz, int indice1, int indice2) {
        int[] temp = matriz[indice1];
        matriz[indice1] = matriz[indice2];
        matriz[indice2] = temp;
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
                mitadCalendario[i] = generarPermutacion(n);;
            }
            intentos++;


            if (checkNoDuplicatesValue(mitadCalendario) && checkNoValuesInColumns(mitadCalendario)){
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
            Set<Integer> valuesColumn = new HashSet<>(matrix.length);

            for (int[] ints : matrix) {
                int absoluteValue = Math.abs(ints[j]);

                if (!valuesColumn.add(absoluteValue)) {
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
     * Verifica si una permutación es válida, es decir, si ningún elemento está en su posición original.
     *
     * @param permutation La permutación a verificar.
     * @return true si la permutación es válida, false en caso contrario.
     */

    private static boolean esPermutacionValida(int[] permutation) {
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
    public static int[] generarPermutacion(int n){
        int[] permutation = IntStream.range(1, n + 1).toArray();

        int intentos = 0;
        int maxIntentos = factorial(n);

        int[] check = new int[n];

        do {
            shuffleArray(permutation);
            System.arraycopy(permutation, 0, check, 0, n);
            intentos++;
        } while (!esPermutacionValida(permutation) && (intentos < maxIntentos));

        if (intentos >= maxIntentos) {
            System.out.println("Se alcanzó el límite de intentos para generar permutaciones válidas.");
            return new int[n]; // O lanzar una excepción según tus necesidades.
        }

        return checkGameTeam(check);
    }

    /**
     * Implementación del algoritmo de Fisher-Yates para permutar un arreglo.
     * @param array El arreglo a permutar.
     */
    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
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

       /* if(IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] > 0)
                .count() == IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] < 0)
                .count()){
        }*/
        // Asinar el signo negativo a la mitad de los equipos.
        IntStream.range(0, permutaciones.length)
                .filter(i -> permutaciones[i] > 0)
                .forEach(i -> permutaciones[permutaciones[i] - 1] = -(i + 1));

        return permutaciones;
    }
    /**
     * Verificar los minimos y maximos de la gira
     * Nota: Permanencia --> Partidos locales, Gira --> Partidos visitantes
     * @param max Maximo paridos consecutivos
     * @param min Minimo partidos consecutivos
     *
     */
    private static boolean MaxMin(int max, int min, int[][] calDep) {
        for (int j = 0; j < calDep[0].length; j++) {
            int cont = 1;
            for (int[] ints : calDep) {
                if (ints[j] > 0) {
                    cont++;
                    if (cont > max) {
                        return false;
                    }
                } else {
                    cont = 0;  // Reiniciar el conteo si encuentra un partido de visita (valor negativo)
                }

                if (cont <= min) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calcula el costo total del calendario de partidos.
     * @param calendario
     * @return
     */
    private static int calcularCosto(int[][] calendario) {
        // Implementa la lógica para calcular el costo total del calendario según tus necesidades.
        // ...
        return 0;
    }
    /**
     * Calcular el factorial de un número.
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
}


