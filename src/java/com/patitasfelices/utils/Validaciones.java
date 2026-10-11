package com.patitasfelices.utils;

import java.util.regex.Pattern;

public class Validaciones {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    public static boolean esEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean esCampoVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public static boolean esEnteroValido(String valor) {
        if (esCampoVacio(valor)) return false;
        try {
            Integer.parseInt(valor.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
