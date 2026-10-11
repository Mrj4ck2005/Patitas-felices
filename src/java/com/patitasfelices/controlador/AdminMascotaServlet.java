package com.patitasfelices.controlador;

import com.patitasfelices.dao.impl.ClienteDAOImpl;
import com.patitasfelices.dao.interfaz.IClienteDAO;
import com.patitasfelices.modelo.Cliente;
import com.patitasfelices.modelo.Mascota;
import com.patitasfelices.servicio.ImagenService;
import com.patitasfelices.servicio.MascotaService;
import com.patitasfelices.servicio.SolicitudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/admin/mascota")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class AdminMascotaServlet extends HttpServlet {

    private final IClienteDAO clienteDAO = new ClienteDAOImpl();
    private final MascotaService mascotaService = new MascotaService();
    private final SolicitudService solicitudService = new SolicitudService();
    private final ImagenService imagenService = new ImagenService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        int idSolicitud = Integer.parseInt(req.getParameter("idSolicitud"));
        int idMascota = Integer.parseInt(req.getParameter("idMascota"));

        // Seguridad: si la mascota ya esta Adoptada, no se permite ninguna edicion,
        // sin importar lo que venga en el formulario (por si alguien lo manipula).
        Mascota actual = mascotaService.obtenerPorId(idMascota);
        if (actual == null || "Adoptado".equals(actual.getEstado())) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        Mascota mascota = new Mascota();
        mascota.setIdMascota(idMascota);
        mascota.setNombre(req.getParameter("nombreMascota"));
        mascota.setEspecie(req.getParameter("especie"));
        mascota.setRaza(req.getParameter("raza"));
        mascota.setEdadMeses(Integer.parseInt(req.getParameter("edadMeses")));
        mascota.setDescripcionSalud(req.getParameter("descripcionSalud"));

        Part filePart = req.getPart("foto");
        String fotoUrl = (filePart != null && filePart.getSize() > 0)
                ? imagenService.subirFoto(filePart)
                : req.getParameter("fotoActual");
        mascota.setFotoUrl(fotoUrl);

        if ("guardarYaprobar".equals(accion)) {
            int idCliente = Integer.parseInt(req.getParameter("idCliente"));

            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setTipoDocumento(req.getParameter("tipoDocumento"));
            cliente.setNumeroDocumento(req.getParameter("numeroDocumento"));
            cliente.setNombreCompleto(req.getParameter("nombreCompleto"));
            cliente.setTelefono(req.getParameter("telefono"));
            cliente.setCorreo(req.getParameter("correo"));
            clienteDAO.actualizar(cliente);

            mascota.setEstado("Pendiente");
            mascotaService.actualizarDatos(mascota);

            solicitudService.aprobarRegistro(idSolicitud, idMascota);

        } else if ("guardarCambios".equals(accion)) {
            // Ya no se hardcodea "Disponible": se conserva el estado real que
            // tenia la mascota (Disponible o Reservado) antes de la edicion.
            mascota.setEstado(actual.getEstado());
            mascotaService.actualizarDatos(mascota);
        }

        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}