package com.patitasfelices.controlador;

import com.patitasfelices.modelo.SolicitudDetalle;
import com.patitasfelices.servicio.MascotaService;
import com.patitasfelices.servicio.SolicitudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({
    "/dashboard",
    "/dashboard/aprobar-registro",
    "/dashboard/rechazar",
    "/dashboard/aprobar-adopcion"
})
public class DashboardServlet extends HttpServlet {

    private final SolicitudService solicitudService = new SolicitudService();
    private final MascotaService mascotaService = new MascotaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Registros de rescate (para aprobar ingresos) y adopciones (historial / aprobar salidas)
        List<SolicitudDetalle> registros = solicitudService.listarDetalladoRegistro();
        List<SolicitudDetalle> adopciones = solicitudService.listarDetalladoAdopcion();

        req.setAttribute("registros", registros);
        req.setAttribute("adopciones", adopciones);

        // Indicadores del dashboard (el estado "Reservado" ya no se usa)
        req.setAttribute("totalDisponibles", mascotaService.contarPorEstado("Disponible"));
        req.setAttribute("totalAdoptadas", mascotaService.contarPorEstado("Adoptado"));
        req.setAttribute("totalSolicitudesPendientes", solicitudService.contarSolicitudesPendientes());

        req.getRequestDispatcher("/WEB-INF/vistas/admin/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ruta = req.getServletPath();
        int idSolicitud = Integer.parseInt(req.getParameter("idSolicitud"));

        if (ruta.endsWith("/aprobar-registro")) {
            int idMascota = Integer.parseInt(req.getParameter("idMascota"));
            solicitudService.aprobarRegistro(idSolicitud, idMascota);

        } else if (ruta.endsWith("/aprobar-adopcion")) {
            int idMascota = Integer.parseInt(req.getParameter("idMascota"));
            solicitudService.aprobarAdopcion(idSolicitud, idMascota);

        } else if (ruta.endsWith("/rechazar")) {
            solicitudService.rechazarSolicitud(idSolicitud);
        }

        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}