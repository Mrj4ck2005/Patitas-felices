package com.patitasfelices.dao.interfaz;

import com.patitasfelices.modelo.Solicitud;
import com.patitasfelices.modelo.SolicitudDetalle;
import java.util.List;

public interface ISolicitudDAO {
    void registrar(Solicitud s);
    List<Solicitud> listarPorTipo(String tipo);
    List<SolicitudDetalle> listarDetalladoPorTipo(String tipo);
    void actualizarEstado(int idSolicitud, String estado);
    int contarPendientes();
    Solicitud buscarPorId(int idSolicitud); // Para la validación de estados de la HU-15

    // Método agregado para la HU-15: Rechaza las demás solicitudes cuando una mascota es adoptada
    void rechazarPendientesPorMascota(int idMascota, String observacion);
}