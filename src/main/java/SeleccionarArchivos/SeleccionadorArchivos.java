/*
package SeleccionarArchivos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
                        manejarExcepcion(ex);
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

                // Inicializar una matriz para almacenar las distancias entre las ciudades sedes
                int[][] distancias = new int[n][n];
                System.out.println("MatrizN de distancias:");
                // Leer las siguientes n líneas para llenar la matriz de distancias
                for (int i = 0; i < n; i++) {
                    String[] valores = lector.readLine().split(" ");
                    for (int j = 0; j < n; j++) {
                        distancias[i][j] = Integer.parseInt(valores[j]);
                        System.out.print(distancias[i][j] + " ");
                    }
                    System.out.println();
                }

                // Creación del planificador con fuerza bruta
                PlanificadorBruto planificador = new PlanificadorBruto(n, distancias, minimo, maximo);

                System.out.println("Evaluando datos...");

                // Resolución del CalDepTournamentScheduler
                int[][] calendario = planificador.resolverCalendarioBruto();

                // Escritura de salida al archivo
                escribirSalida("src/main/Datos/salida.txt", n, minimo, maximo, calendario);

                System.out.println("Salida generada con éxito.");
                planificador.imprimirMejorCalendario();

            } catch (NumberFormatException e) {
                manejarExcepcion(new DatosInvalidosException("Error: Los datos no son válidos."));
            } catch (Exception e) {
                manejarExcepcion(new DatosInvalidosException("Error desconocido al procesar el archivo."));
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
                manejarExcepcion(new DatosInvalidosException("Error al escribir el archivo de salida."));
            }
        }

        private void manejarExcepcion(DatosInvalidosException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        class DatosInvalidosException extends Exception {
            public DatosInvalidosException(String mensaje) {
                super(mensaje);
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(SeleccionadorArchivos::new);
        }
    }*/
