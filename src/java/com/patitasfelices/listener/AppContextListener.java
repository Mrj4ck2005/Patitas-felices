package com.patitasfelices.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Se ejecuta al iniciar/detener la aplicacion.
 * Punto recomendado para inicializar un pool de conexiones (HikariCP)
 * en avances futuros, en vez de abrir una conexion nueva por request.
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Refugio Patitas Felices: aplicacion iniciada ===");
        // TODO (avance futuro): inicializar pool de conexiones aqui
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Refugio Patitas Felices: aplicacion detenida ===");
    }
}
