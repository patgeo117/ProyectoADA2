package FuerzaBruta;

public class PlanificadorBruto {
    private int[][] mejorCalendario;
    private double mejorCosto;
    private int[][] distancias;

    public PlanificadorBruto(int n, int[][] distancias, int Min, int Max) {
        mejorCalendario = new int[2 * (n - 1)][n];
        mejorCosto = Double.POSITIVE_INFINITY;
        this.distancias = distancias;
    }

    private void intercambiar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private double calcularCosto(int[] equipoIndices) {
        int n = equipoIndices.length;
        double costoTotal = 0;

        for (int i = 0; i < n; i++) {
            int equipoActual = equipoIndices[i];
            int equipoSiguiente = equipoIndices[(i + 1) % n];
            costoTotal += obtenerDistancia(equipoActual, equipoSiguiente);
        }

        return costoTotal;
    }

    private int obtenerDistancia(int equipoOrigen, int equipoDestino) {
        return distancias[equipoOrigen][equipoDestino];
    }

    private void copiarPermutacion(int[] equipoIndices, int permutacionActual) {
        int n = equipoIndices.length;

        // Copiar la permutación en la fila correspondiente
        for (int i = 0; i < n; i++) {
            mejorCalendario[permutacionActual][i] = equipoIndices[i] + 1;
        }
    }

    public int[][] resolverCalendarioBruto() {
        int n = mejorCalendario[0].length;
        int[] equipoIndices = new int[n];
        for (int i = 0; i < n; i++) {
            equipoIndices[i] = i;
        }

        permutaciones(equipoIndices, 1, n, 0); // Pasa 0 como índice de permutación

        return mejorCalendario;
    }


    private void permutaciones(int[] equipoIndices, int inicio, int n, int permutacionActual) {
        if (inicio == n) {
            double costoActual = calcularCosto(equipoIndices);
            if (costoActual < mejorCosto) {
                mejorCosto = costoActual;
                copiarPermutacion(equipoIndices, permutacionActual);
            }
        } else {
            for (int i = inicio; i < n; i++) {
                intercambiar(equipoIndices, inicio, i);
                permutaciones(equipoIndices, inicio + 1, n, permutacionActual + 1); // Incrementa el índice de permutación
                intercambiar(equipoIndices, inicio, i);
            }
        }
    }

    public void imprimirMejorCalendario() {
        int n = mejorCalendario[0].length;
        System.out.println("Mejor calendario:");
        for (int i = 0; i < 2 * (n - 1); i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(mejorCalendario[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Costo: " + mejorCosto);
    }
}

