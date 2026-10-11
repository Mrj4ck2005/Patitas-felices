package com.patitasfelices.modelo;

import java.sql.Timestamp;

public class Adopcion {
    private int idAdopcion;
    private int idMascota;
    private int idCliente;
    private int idSolicitud;
    private Timestamp fechaAdopcion;
    private String estadoAdopcion; // Pendiente / Confirmada

    public Adopcion() {}

    public int getIdAdopcion() { return idAdopcion; }
    public void setIdAdopcion(int idAdopcion) { this.idAdopcion = idAdopcion; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public Timestamp getFechaAdopcion() { return fechaAdopcion; }
    public void setFechaAdopcion(Timestamp fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }

    public String getEstadoAdopcion() { return estadoAdopcion; }
    public void setEstadoAdopcion(String estadoAdopcion) { this.estadoAdopcion = estadoAdopcion; }
}
//nuevo codigo solucionando el porblema de tb_adopciones