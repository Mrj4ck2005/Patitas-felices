package com.patitasfelices.dao.interfaz;

import com.patitasfelices.modelo.Mascota;
import java.util.List;

public interface IMascotaDAO {
    int registrar(Mascota mascota);
    List<Mascota> listarDisponibles(String especie, String raza);
    List<Mascota> listarTodas();
    Mascota buscarPorId(int idMascota);
    void actualizarEstado(int idMascota, String nuevoEstado);
    void actualizar(Mascota mascota);
    int contarPorEstado(String estado); // nuevo: para las tarjetas del dashboard
}