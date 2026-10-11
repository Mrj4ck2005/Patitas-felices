package com.patitasfelices.modelo;

public class Mascota {
    private int idMascota;
    private int idCliente;
    private String nombre;
    private String especie;
    private String raza;
    private int edadMeses;
    private String descripcionSalud;
    private String fotoUrl;     // URL de Cloudinary
    private String estado;      // Pendiente / Disponible / Adoptado / Rechazado

    public Mascota() {}

    public Mascota(int idCliente, String nombre, String especie, String raza,
                    int edadMeses, String descripcionSalud, String fotoUrl, String estado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edadMeses = edadMeses;
        this.descripcionSalud = descripcionSalud;
        this.fotoUrl = fotoUrl;
        this.estado = estado;
    }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
