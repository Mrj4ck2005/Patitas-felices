package com.patitasfelices.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para cargar las credenciales desde app.properties.
 * Esto evita quemar contraseñas en el código fuente.
 */
public class Config {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new RuntimeException("Alerta de Seguridad: No se encontró el archivo app.properties. Por favor créalo basándote en app.properties.example");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando la configuración: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}