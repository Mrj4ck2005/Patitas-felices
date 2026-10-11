package com.patitasfelices.servicio;

import com.patitasfelices.dao.impl.SolicitudDAOImpl;
import com.patitasfelices.dao.impl.AdopcionDAOImpl;
import com.patitasfelices.dao.interfaz.ISolicitudDAO;
import com.patitasfelices.dao.interfaz.IAdopcionDAO;
import com.patitasfelices.modelo.Solicitud;
import com.patitasfelices.modelo.SolicitudDetalle;
import com.patitasfelices.modelo.Adopcion;

import java.util.List;

public class SolicitudService {

    private final ISolicitudDAO solicitudDAO = new SolicitudDAOImpl();
    private final IAdopcionDAO adopcionDAO = new AdopcionDAOImpl();
    private final MascotaService mascotaService = new MascotaService();

    public void registrarSolicitudDeMascota(Solicitud s) {
        s.setTipoSolicitud("REGISTRO_MASCOTA");
        solicitudDAO.registrar(s);
    }

    public void registrarSolicitudDeAdopcion(Solicitud s) {
        s.setTipoSolicitud("ADOPCION");
        solicitudDAO.registrar(s);
    }

    public List<Solicitud> listarSolicitudesRegistro() {
        return solicitudDAO.listarPorTipo("REGISTRO_MASCOTA");
    }

    public List<Solicitud> listarSolicitudesAdopcion() {
        return solicitudDAO.listarPorTipo("ADOPCION");
    }

    public List<SolicitudDetalle> listarDetalladoRegistro() {
        return solicitudDAO.listarDetalladoPorTipo("REGISTRO_MASCOTA");
    }

    public List<SolicitudDetalle> listarDetalladoAdopcion() {
        return solicitudDAO.listarDetalladoPorTipo("ADOPCION");
    }

    // ==========================================
    // HU-15: CORRECCIÓN DE APROBACIONES
    // ==========================================

    public void aprobarRegistro(int idSolicitud, int idMascota) {
        // 1. Buscamos la solicitud actual para verificar su estado
        Solicitud solicitud = solicitudDAO.buscarPorId(idSolicitud);
        
        // 2. Si ya no está "Pendiente" (ej. alguien dio "Atrás" y volvió a enviar), bloqueamos.
        if (solicitud == null || !"Pendiente".equals(solicitud.getEstadoTramite())) {
            throw new RuntimeException("Error: Esta solicitud ya fue procesada anteriormente.");
        }

        // Si pasa la validación, procedemos con normalidad
        solicitudDAO.actualizarEstado(idSolicitud, "Aprobado");
        mascotaService.publicarEnVitrina(idMascota);
    }

    public void aprobarAdopcion(int idSolicitud, int idMascota) {
        // 1. Buscamos la solicitud actual para verificar su estado
        Solicitud solicitud = solicitudDAO.buscarPorId(idSolicitud);
        
        // 2. Validamos que la solicitud siga pendiente
        if (solicitud == null || !"Pendiente".equals(solicitud.getEstadoTramite())) {
            throw new RuntimeException("Error: Esta solicitud de adopción ya no está pendiente.");
        }

        // 3. Validamos que la mascota siga disponible llamando a tu servicio de mascotas
        // (Asumo que tienes un método buscarPorId en MascotaService. Si no, usa el DAO de mascota)
        // Ejemplo genérico (ajusta según tu MascotaService):
        // Mascota m = mascotaService.buscarMascota(idMascota);
        // if (!"Disponible".equals(m.getEstado())) { throw new RuntimeException("..."); }

        // Si pasa las validaciones, aprobamos
        solicitudDAO.actualizarEstado(idSolicitud, "Aprobado");
        mascotaService.marcarComoAdoptada(idMascota);

        Adopcion adopcion = new Adopcion();
        adopcion.setIdSolicitud(idSolicitud);
        adopcion.setIdMascota(idMascota); // Asumiendo que tu tabla adopciones tiene este campo
        adopcion.setEstadoAdopcion("Confirmada");
        adopcionDAO.registrar(adopcion);

        // 4. HU-15 MAGIA: Rechazar a los demás postulantes automáticamente
        // Requiere que crees este método en SolicitudDAOImpl (te dejé el código en mi respuesta anterior)
        solicitudDAO.rechazarPendientesPorMascota(idMascota, "Mascota adoptada por otra solicitud.");
    }

    // ==========================================

    public void rechazarSolicitud(int idSolicitud) {
        solicitudDAO.actualizarEstado(idSolicitud, "Rechazado");
    }

    public int contarSolicitudesPendientes() {
        return solicitudDAO.contarPendientes();
    }
}