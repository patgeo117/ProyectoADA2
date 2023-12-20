package SeleccionarArchivos;

import Planificador.CalendarioGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class SeleccionadorArchivos extends JFrame {
        private static final String CARPETA_PREDETERMINADA = "src/main/Datos";

        public SeleccionadorArchivos() {
            configurarVentana();
            agregarBotonAbrirArchivo();
            mostrarVentana();
        }

        private void configurarVentana() {
            setTitle("Seleccionador de Archivos");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private void agregarBotonAbrirArchivo() {
            JButton abrirBoton = new JButton("Abrir Archivo");
            abrirBoton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        seleccionarArchivo();
                        dispose();
                    } catch (DatosInvalidosException ex) {
                        ex.manejarExcepcion(ex);
                    }
                }
            });
            add(abrirBoton);
        }

        private void mostrarVentana() {
            setVisible(true);
        }

        private void seleccionarArchivo() throws DatosInvalidosException {
            JFileChooser fileChooser = new JFileChooser(CARPETA_PREDETERMINADA);
            int resultado = fileChooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                leerArchivo(archivo);
            }
        }

        private void leerArchivo(File archivo) throws DatosInvalidosException {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                // Leer la primera línea para obtener el número de equipos (n)
                int n = Integer.parseInt(lector.readLine());
                // Leer la segunda línea para obtener el tamaño mínimo de gira o permanencia (Mínimo)
                int minimo = Integer.parseInt(lector.readLine());
                // Leer la tercera línea para obtener el tamaño máximo de gira o permanencia (Máximo)
                int maximo = Integer.parseInt(lector.readLine());

                System.out.println("Número de equipos: " + n);
                System.out.println("Tamaño mínimo: " + minimo);
                System.out.println("Tamaño máximo: " + maximo);

                /*// Inicializar una matriz para almacenar las distancias entre las ciudades sedes
                int[][] distancias = new int[n][n];
                System.out.println("Matriz de distancias:");
                // Leer las siguientes n líneas para llenar la matriz de distancias
                for (int i = 0; i < n; i++) {
                    String[] valores = lector.readLine().split(" ");
                    for (int j = 0; j < n; j++) {
                        distancias[i][j] = Integer.parseInt(valores[j]);
                        System.out.print(distancias[i][j] + " ");
                    }
                    System.out.println();
                }*/


                System.out.println("\nEvaluando datos...");

                CalendarioGenerator calendarioGenerator= new CalendarioGenerator();

                try {
                    validarNumeroEquipos(n);
                    int[][] calendario = calendarioGenerator.generarCalendario(n, maximo, minimo);
                    imprimirCalendario(calendario);

                    // Resto del código
                } catch (NumeroEquiposImparException e) {
                    System.out.println("Error: El número de equipos debe ser par.");
                }


            } catch (NumberFormatException e) {
                DatosInvalidosException ex = new DatosInvalidosException("Error al leer el archivo: formato de número inválido.");
                ex.manejarExcepcion(ex);
            } catch (Exception e) {
                DatosInvalidosException ex = new DatosInvalidosException("Error al leer el archivo.");
                ex.manejarExcepcion(ex);
            }
        }

        private void escribirSalida(String nombreArchivo, int n, int minimo, int maximo, int[][] calendario) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                writer.write(Integer.toString(n));
                writer.newLine();
                writer.write(Integer.toString(minimo));
                writer.newLine();
                writer.write(Integer.toString(maximo));
                writer.newLine();
                for (int i = 0; i < 2 * (n - 1); i++) {
                    for (int j = 0; j < n; j++) {
                        writer.write(calendario[i][j] + " ");
                    }
                    writer.newLine();
                }
            } catch (IOException e) {
                DatosInvalidosException ex = new DatosInvalidosException("Error al escribir el archivo de salida.");
                ex.manejarExcepcion(ex);
            }
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

        public static void main(String[] args) {
            SwingUtilities.invokeLater(SeleccionadorArchivos::new);
    }
}
