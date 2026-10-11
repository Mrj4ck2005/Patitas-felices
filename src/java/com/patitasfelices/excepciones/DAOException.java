package com.patitasfelices.excepciones;

/** Excepcion personalizada para envolver errores de acceso a datos. */
public class DAOException extends RuntimeException {
    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    public DAOException(String mensaje) {
        super(mensaje);
    }
}
