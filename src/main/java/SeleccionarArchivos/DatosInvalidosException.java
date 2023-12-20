package SeleccionarArchivos;

public class DatosInvalidosException extends Exception {
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
    public void manejarExcepcion(DatosInvalidosException ex) {
        System.err.println(ex.getMessage());
        ex.printStackTrace();
    }
}
