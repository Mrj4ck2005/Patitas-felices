package com.patitasfelices.controlador;

import com.patitasfelices.modelo.Mascota;
import com.patitasfelices.servicio.MascotaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controla la vista publica del catalogo (vitrina de adopcion) con filtros
 * por especie y raza. Ruta: /adoptar
 */
@WebServlet("/adoptar")
public class MascotaServlet extends HttpServlet {

    private final MascotaService mascotaService = new MascotaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String especie = req.getParameter("especie");
        String raza = req.getParameter("raza");

        List<Mascota> mascotas = mascotaService.obtenerCatalogoDisponible(especie, raza);
        req.setAttribute("mascotas", mascotas);

        req.getRequestDispatcher("/WEB-INF/vistas/publico/adoptar.jsp").forward(req, resp);
    }
}
