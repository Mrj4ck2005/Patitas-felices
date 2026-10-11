package com.patitasfelices.servicio;

import com.patitasfelices.dao.impl.MascotaDAOImpl;
import com.patitasfelices.dao.interfaz.IMascotaDAO;
import com.patitasfelices.modelo.Mascota;

import java.util.List;

public class MascotaService {

    private final IMascotaDAO mascotaDAO = new MascotaDAOImpl();

    public int registrarMascota(Mascota m) {
        m.setEstado("Pendiente");
        return mascotaDAO.registrar(m);
    }

    public List<Mascota> obtenerCatalogoDisponible(String especie, String raza) {
        return mascotaDAO.listarDisponibles(especie, raza);
    }

    public List<Mascota> obtenerTodas() {
        return mascotaDAO.listarTodas();
    }

    public Mascota obtenerPorId(int idMascota) {
        return mascotaDAO.buscarPorId(idMascota);
    }

    public void publicarEnVitrina(int idMascota) {
        mascotaDAO.actualizarEstado(idMascota, "Disponible");
    }

    public void marcarComoAdoptada(int idMascota) {
        mascotaDAO.actualizarEstado(idMascota, "Adoptado");
    }

    public void actualizarDatos(Mascota m) {
        mascotaDAO.actualizar(m);
    }

    public int contarPorEstado(String estado) {
        return mascotaDAO.contarPorEstado(estado);
    }
}