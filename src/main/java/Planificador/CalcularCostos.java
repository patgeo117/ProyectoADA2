package Planificador;

/**
 * Calcula el costo total del calendario de partidos.
 */
public class CalcularCostos {

    public int calcularCosto(int[][] costo, int[][] calendario) {
        // Caso uno de los equipos no juega en casa
        //Nota: Los equipos locales no suman costo
        int costoTotal = 0;
        for (int j = 0; j < calendario[0].length; j++) {
            int posicionLocal = j;

            for (int i = 0; i < calendario.length; i++) {
                int posicionVisitante;

                // Caso uno. Los equipos jugan de visita
                if (calendario[i][j] < 0) {
                    posicionVisitante = (calendario[i][j] * -1)-1;
                    costoTotal += costo[posicionLocal][posicionVisitante];
                    posicionLocal = posicionVisitante;
                }

                if (!(i== calendario.length - 1)){
                    // Caso dos. Los equipos juegan de local y el siguiente de visitante
                    if (calendario[i][j] > 0 && calendario[i+1][j] < 0) {
                        posicionVisitante = (calendario[i+1][j] * -1)-1;
                        costoTotal += costo[j][posicionVisitante];
                        posicionLocal = posicionVisitante;
                    }
                    // Caso tres. Los equipos juegan de visita y van de local en el siguiente partido
                    if (calendario[i][j] < 0 && calendario[i+1][j] > 0) {
                        posicionVisitante = (calendario[i][j] *-1)-1;
                        costoTotal += costo[j][posicionVisitante];
                        posicionLocal = posicionVisitante;
                    }
                }

                // Caso cuatro. Él termina de local y necesita devolverse a casa
                if (i == calendario.length-1 && calendario[i][j] < 0) {
                    costoTotal += costo[j][posicionLocal];
                }
            }
        }
        System.out.println("El costo total es: " + costoTotal);
        return costoTotal;
    }
}
