package FuerzaBruta;

import java.util.*;
import java.util.Collections;

public class projectIngenuoTwo {

    public static void main(String[] args) {
        int n = 4; // El número de equipos
        int[][] mitadCalendario = generateHalfCalendar(n);

        // Imprimir la matriz mitadCalendario
        for (int[] row : mitadCalendario) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[][] generateHalfCalendar(int n) {
        int[][] mitadCalendario = new int[n - 1][n];
        int[] elements = new int[n];

        for (int i = 0; i < n; i++) {
            elements[i] = i + 1;
        }

        generatePermutations(mitadCalendario, elements, n);
        return mitadCalendario;
    }

    public static void generatePermutations(int[][] mitadCalendario, int[] elements, int size) {
        if (size == 1) {
            // Copiar el array de elementos en la matriz mitadCalendario
            for (int i = 0; i < mitadCalendario.length; i++) {
                mitadCalendario[i][elements[0] - 1] = elements[i];
            }
        } else {
            for (int i = 0; i < size; i++) {
                generatePermutations(mitadCalendario, elements, size - 1);

                if (size % 2 == 1) {
                    swap(elements, 0, size - 1);
                } else {
                    swap(elements, i, size - 1);
                }
            }
        }
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}



