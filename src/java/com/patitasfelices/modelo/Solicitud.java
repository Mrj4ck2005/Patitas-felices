package com.patitasfelices.modelo;

import java.sql.Timestamp;

public class Solicitud {
    private int idSolicitud;
    private int idMascota;
    private int idCliente;
    private String tipoSolicitud;  // REGISTRO_MASCOTA / ADOPCION
    private Timestamp fechaRegistro;
    private String estadoTramite;  // Pendiente / Aprobado / Rechazado

    // Campos extra usados solo para mostrar en el dashboard (JOIN)
    private String nombreMascota;
    private String nombreCliente;

    public Solicitud() {}

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstadoTramite() { return estadoTramite; }
    public void setEstadoTramite(String estadoTramite) { this.estadoTramite = estadoTramite; }

    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
}
