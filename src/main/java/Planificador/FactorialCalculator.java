package Planificador;

/**
 * Esta clase proporciona un método estático para calcular el factorial de un número.
 */
public class FactorialCalculator {

    /**
     * Calcula el factorial de un número dado.
     *
     * @param n El número para el cual se calculará el factorial.
     * @return El factorial de n.
     */
    public static int factorial(int n) {
        int factorial = 1;

        // Itera desde 2 hasta n y multiplica los números en cada iteración
        for (int i = 2; i <= n; i++) {
            factorial *= i;
        }

        return factorial;
    }
}

