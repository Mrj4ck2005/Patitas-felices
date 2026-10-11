package com.patitasfelices.dao.impl;

import com.patitasfelices.config.Conexion;
import com.patitasfelices.dao.interfaz.IMascotaDAO;
import com.patitasfelices.excepciones.DAOException;
import com.patitasfelices.modelo.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAOImpl implements IMascotaDAO {

    @Override
    public int registrar(Mascota m) {
        String sql = "INSERT INTO tb_mascotas (id_cliente, nombre, especie, raza, edad_meses, "
                + "descripcion_salud, foto_url, estado) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getIdCliente());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getEspecie());
            ps.setString(4, m.getRaza());
            ps.setInt(5, m.getEdadMeses());
            ps.setString(6, m.getDescripcionSalud());
            ps.setString(7, m.getFotoUrl());
            ps.setString(8, m.getEstado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new DAOException("No se pudo obtener el id de la mascota generada", null);
        } catch (SQLException e) {
            throw new DAOException("Error al registrar mascota", e);
        }
    }

    @Override
    public List<Mascota> listarDisponibles(String especie, String raza) {
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM tb_mascotas WHERE estado = 'Disponible'");
        if (especie != null && !especie.isEmpty() && !especie.equalsIgnoreCase("Todos")) {
            sql.append(" AND especie = ?");
        }
        if (raza != null && !raza.isEmpty() && !raza.equalsIgnoreCase("Todas las Razas")) {
            sql.append(" AND raza = ?");
        }
        List<Mascota> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            if (especie != null && !especie.isEmpty() && !especie.equalsIgnoreCase("Todos")) {
                ps.setString(idx++, especie);
            }
            if (raza != null && !raza.isEmpty() && !raza.equalsIgnoreCase("Todas las Razas")) {
                ps.setString(idx++, raza);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar mascotas disponibles", e);
        }
        return lista;
    }

    @Override
    public List<Mascota> listarTodas() {
        String sql = "SELECT * FROM tb_mascotas ORDER BY id_mascota DESC";
        List<Mascota> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new DAOException("Error al listar mascotas", e);
        }
        return lista;
    }

    @Override
    public Mascota buscarPorId(int idMascota) {
        String sql = "SELECT * FROM tb_mascotas WHERE id_mascota = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar mascota", e);
        }
        return null;
    }

    @Override
    public void actualizarEstado(int idMascota, String nuevoEstado) {
        String sql = "UPDATE tb_mascotas SET estado = ? WHERE id_mascota = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idMascota);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar estado de mascota", e);
        }
    }

    @Override
    public void actualizar(Mascota m) {
        String sql = "UPDATE tb_mascotas SET nombre=?, especie=?, raza=?, edad_meses=?, "
                + "descripcion_salud=?, foto_url=?, estado=? WHERE id_mascota=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setString(3, m.getRaza());
            ps.setInt(4, m.getEdadMeses());
            ps.setString(5, m.getDescripcionSalud());
            ps.setString(6, m.getFotoUrl());
            ps.setString(7, m.getEstado());
            ps.setInt(8, m.getIdMascota());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar mascota", e);
        }
    }

    @Override
    public int contarPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM tb_mascotas WHERE estado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al contar mascotas por estado", e);
        }
        return 0;
    }

    private Mascota mapear(ResultSet rs) throws SQLException {
        Mascota m = new Mascota();
        m.setIdMascota(rs.getInt("id_mascota"));
        m.setIdCliente(rs.getInt("id_cliente"));
        m.setNombre(rs.getString("nombre"));
        m.setEspecie(rs.getString("especie"));
        m.setRaza(rs.getString("raza"));
        m.setEdadMeses(rs.getInt("edad_meses"));
        m.setDescripcionSalud(rs.getString("descripcion_salud"));
        m.setFotoUrl(rs.getString("foto_url"));
        m.setEstado(rs.getString("estado"));
        return m;
    }
}