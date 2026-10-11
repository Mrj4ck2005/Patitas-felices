package com.patitasfelices.controlador;

import com.patitasfelices.modelo.Cliente;
import com.patitasfelices.modelo.Mascota;
import com.patitasfelices.modelo.Solicitud;
import com.patitasfelices.servicio.ImagenService;
import com.patitasfelices.servicio.MascotaService;
import com.patitasfelices.servicio.SolicitudService;
import com.patitasfelices.dao.impl.ClienteDAOImpl;
import com.patitasfelices.dao.interfaz.IClienteDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet({"/dar-adopcion", "/solicitar-adopcion"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB por foto
public class SolicitudServlet extends HttpServlet {

    private final IClienteDAO clienteDAO = new ClienteDAOImpl();
    private final MascotaService mascotaService = new MascotaService();
    private final SolicitudService solicitudService = new SolicitudService();
    private final ImagenService imagenService = new ImagenService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/publico/dar_adopcion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ruta = req.getServletPath();
        String numeroDoc = req.getParameter("numeroDocumento");

        // Datos comunes del cliente
        Cliente cliente = new Cliente();
        cliente.setTipoDocumento(req.getParameter("tipoDocumento") != null ? req.getParameter("tipoDocumento") : "DNI");
        cliente.setNumeroDocumento(numeroDoc);
        cliente.setNombreCompleto(req.getParameter("nombreCompleto"));
        cliente.setTelefono(req.getParameter("telefono"));
        cliente.setCorreo(req.getParameter("correo"));

        // ===== SOLUCIÓN AL ERROR DE CLIENTE DUPLICADO =====
        int idCliente = -1;
        Cliente clienteExistente = clienteDAO.buscarPorDocumento(cliente.getTipoDocumento(), numeroDoc); // Busca si el DNI ya está registrado
        
        if (clienteExistente != null) {
            // Si el cliente ya existe, reutilizamos su ID
            idCliente = clienteExistente.getIdCliente();
        } else {
            // Si es un cliente nuevo, le asignamos el tipo y lo registramos en la BD
            String tipoCliente = "/dar-adopcion".equals(ruta) ? "RESCATISTA" : "ADOPTANTE";
            cliente.setTipoCliente(tipoCliente);
            idCliente = clienteDAO.registrar(cliente);
        }
        // ===================================================

        if ("/dar-adopcion".equals(ruta)) {
            Part filePart = req.getPart("foto");
            String fotoUrl = (filePart != null && filePart.getSize() > 0)
                    ? imagenService.subirFoto(filePart)
                    : "https://res.cloudinary.com/demo/image/upload/placeholder_mascota.jpg";

            Mascota mascota = new Mascota();
            mascota.setIdCliente(idCliente);
            mascota.setNombre(req.getParameter("nombreMascota"));
            mascota.setEspecie(req.getParameter("especie"));
            mascota.setRaza(req.getParameter("raza"));
            mascota.setEdadMeses(Integer.parseInt(req.getParameter("edadMeses")));
            mascota.setDescripcionSalud(req.getParameter("descripcionSalud"));
            mascota.setFotoUrl(fotoUrl);

            int idMascota = mascotaService.registrarMascota(mascota);

            Solicitud solicitud = new Solicitud();
            solicitud.setIdCliente(idCliente);
            solicitud.setIdMascota(idMascota); 
            solicitudService.registrarSolicitudDeMascota(solicitud);

            resp.sendRedirect(req.getContextPath() + "/index.jsp?msg=solicitud_enviada");

        } else if ("/solicitar-adopcion".equals(ruta)) {
            int idMascota = Integer.parseInt(req.getParameter("idMascota"));

            Solicitud solicitud = new Solicitud();
            solicitud.setIdCliente(idCliente);
            solicitud.setIdMascota(idMascota);
            solicitudService.registrarSolicitudDeAdopcion(solicitud);

            // Redirección con el mensaje para activar el overlay en adoptar.jsp
            resp.sendRedirect(req.getContextPath() + "/adoptar?msg=adopcion_enviada");
        }
    }
}