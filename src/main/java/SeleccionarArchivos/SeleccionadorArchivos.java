package SeleccionarArchivos;

import FuerzaBruta.PlanificadorBruto;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SeleccionadorArchivos extends JFrame {
    // Constructor de la clase
    public SeleccionadorArchivos() {
        configurarVentana();
        agregarBotonAbrirArchivo();
        mostrarVentana();
    }

    // Configurar la ventana
    private void configurarVentana() {
        setTitle("Seleccionador de Archivos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Agregar el botón para abrir el selector de archivos
    private void agregarBotonAbrirArchivo() {
        JButton abrirBoton = new JButton("Abrir Archivo");
        abrirBoton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    seleccionarArchivo();
                    // Después de seleccionar el archivo y ejecutar la solución, cerrar la interfaz
                    dispose();
                } catch (DatosInvalidosException ex) {
                    manejarExcepcion(ex);
                }
            }
        });
        add(abrirBoton);
    }

    // Mostrar la ventana
    private void mostrarVentana() {
        setVisible(true);
    }

    // Método para abrir el selector de archivos
    private void seleccionarArchivo() throws DatosInvalidosException {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            leerArchivo(archivo);
        }
    }

    // Método para leer y procesar el archivo
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
            System.out.println("Matriz de distancias:");
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

            // Resolución del calendario
            int[][] calendario = planificador.resolverCalendarioBruto();

            // Escritura de salida al archivo
            escribirSalida("src/main/SalidaDatos/salida.txt", n, minimo, maximo, calendario);

            System.out.println("Salida generada con éxito.");
            planificador.imprimirMejorCalendario();

        } catch (NumberFormatException e) {
            // Manejar la excepción si ocurre un error al convertir datos a números
            manejarExcepcion(new DatosInvalidosException("Error: Los datos no son válidos."));
        } catch (Exception e) {
            // Manejar una excepción general si ocurre algún otro error al procesar el archivo
            manejarExcepcion(new DatosInvalidosException("Error desconocido al procesar el archivo."));
        }
    }

    // Método para escribir la salida al archivo
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
                    writer.write(Integer.toString(calendario[i][j]) + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            manejarExcepcion(new DatosInvalidosException("Error al escribir el archivo de salida."));
        }
    }

    // Manejar excepción
    private void manejarExcepcion(DatosInvalidosException ex) {
        // Imprimir el mensaje de la excepción y la traza de la pila
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }

    // Definición de la excepción personalizada
    class DatosInvalidosException extends Exception {
        public DatosInvalidosException(String mensaje) {
            super(mensaje);
        }
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        // Iniciar la aplicación en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new SeleccionadorArchivos());
    }
}
