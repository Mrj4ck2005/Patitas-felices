package com.patitasfelices.modelo;

import java.sql.Timestamp;

/**
 * DTO (Data Transfer Object): agrupa en un solo objeto los datos de
 * tb_solicitudes + tb_clientes + tb_mascotas, para mostrar todo junto
 * en el modal de "Editar"/"Ver" del panel admin sin hacer 3 consultas separadas.
 */
public class SolicitudDetalle {

    // --- Datos de la solicitud ---
    private int idSolicitud;
    private String tipoSolicitud;
    private Timestamp fechaRegistro;
    private String estadoTramite;
    private String observacion;

    // --- Datos del cliente (rescatista o adoptante) ---
    private int idCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreCompleto;
    private String telefono;
    private String correo;

    // --- Datos de la mascota ---
    private int idMascota;
    private String nombreMascota;
    private String especie;
    private String raza;
    private int edadMeses;
    private String descripcionSalud;
    private String fotoUrl;
    private String estadoMascota;

    public SolicitudDetalle() {}

    // Getters y setters

    public int getIdSolicitud() { return idSolicitud; }
    public void setIdSolicitud(int idSolicitud) { this.idSolicitud = idSolicitud; }

    public String getTipoSolicitud() { return tipoSolicitud; }
    public void setTipoSolicitud(String tipoSolicitud) { this.tipoSolicitud = tipoSolicitud; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstadoTramite() { return estadoTramite; }
    public void setEstadoTramite(String estadoTramite) { this.estadoTramite = estadoTramite; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdadMeses() { return edadMeses; }
    public void setEdadMeses(int edadMeses) { this.edadMeses = edadMeses; }

    public String getDescripcionSalud() { return descripcionSalud; }
    public void setDescripcionSalud(String descripcionSalud) { this.descripcionSalud = descripcionSalud; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getEstadoMascota() { return estadoMascota; }
    public void setEstadoMascota(String estadoMascota) { this.estadoMascota = estadoMascota; }
} 
//nuevo archivo de la version 2 