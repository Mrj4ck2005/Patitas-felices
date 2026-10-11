package com.patitasfelices.dao.impl;

import com.patitasfelices.config.Conexion;
import com.patitasfelices.dao.interfaz.ISolicitudDAO;
import com.patitasfelices.excepciones.DAOException;
import com.patitasfelices.modelo.Solicitud;
import com.patitasfelices.modelo.SolicitudDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAOImpl implements ISolicitudDAO {

    @Override
    public void registrar(Solicitud s) {
        String sql = "INSERT INTO tb_solicitudes (id_mascota, id_cliente, tipo_solicitud, estado_tramite) "
                + "VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getIdMascota());
            ps.setInt(2, s.getIdCliente());
            ps.setString(3, s.getTipoSolicitud());
            ps.setString(4, s.getEstadoTramite() == null ? "Pendiente" : s.getEstadoTramite());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al registrar solicitud", e);
        }
    }

    @Override
    public List<Solicitud> listarPorTipo(String tipoSolicitud) {
        String sql = "SELECT s.*, m.nombre AS nombre_mascota, c.nombre_completo AS nombre_cliente "
                + "FROM tb_solicitudes s "
                + "JOIN tb_mascotas m ON s.id_mascota = m.id_mascota "
                + "JOIN tb_clientes c ON s.id_cliente = c.id_cliente "
                + "WHERE s.tipo_solicitud = ? ORDER BY s.fecha_registro DESC";
        List<Solicitud> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Solicitud s = mapear(rs);
                    s.setNombreMascota(rs.getString("nombre_mascota"));
                    s.setNombreCliente(rs.getString("nombre_cliente"));
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar solicitudes", e);
        }
        return lista;
    }

    @Override
    public List<SolicitudDetalle> listarDetalladoPorTipo(String tipoSolicitud) {
        String sql = "SELECT s.id_solicitud, s.tipo_solicitud, s.fecha_registro, s.estado_tramite, s.observacion, "
                + "c.id_cliente, c.tipo_documento, c.numero_documento, c.nombre_completo, c.telefono, c.correo, "
                + "m.id_mascota, m.nombre AS nombre_mascota, m.especie, m.raza, m.edad_meses, "
                + "m.descripcion_salud, m.foto_url, m.estado AS estado_mascota "
                + "FROM tb_solicitudes s "
                + "JOIN tb_mascotas m ON s.id_mascota = m.id_mascota "
                + "JOIN tb_clientes c ON s.id_cliente = c.id_cliente "
                + "WHERE s.tipo_solicitud = ? ORDER BY s.fecha_registro DESC";

        List<SolicitudDetalle> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SolicitudDetalle d = new SolicitudDetalle();
                    d.setIdSolicitud(rs.getInt("id_solicitud"));
                    d.setTipoSolicitud(rs.getString("tipo_solicitud"));
                    d.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    d.setEstadoTramite(rs.getString("estado_tramite"));
                    d.setObservacion(rs.getString("observacion"));

                    d.setIdCliente(rs.getInt("id_cliente"));
                    d.setTipoDocumento(rs.getString("tipo_documento"));
                    d.setNumeroDocumento(rs.getString("numero_documento"));
                    d.setNombreCompleto(rs.getString("nombre_completo"));
                    d.setTelefono(rs.getString("telefono"));
                    d.setCorreo(rs.getString("correo"));

                    d.setIdMascota(rs.getInt("id_mascota"));
                    d.setNombreMascota(rs.getString("nombre_mascota"));
                    d.setEspecie(rs.getString("especie"));
                    d.setRaza(rs.getString("raza"));
                    d.setEdadMeses(rs.getInt("edad_meses"));
                    d.setDescripcionSalud(rs.getString("descripcion_salud"));
                    d.setFotoUrl(rs.getString("foto_url"));
                    d.setEstadoMascota(rs.getString("estado_mascota"));

                    lista.add(d);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar solicitudes detalladas", e);
        }
        return lista;
    }

    @Override
    public Solicitud buscarPorId(int idSolicitud) {
        String sql = "SELECT * FROM tb_solicitudes WHERE id_solicitud = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar solicitud", e);
        }
        return null;
    }

    @Override
    public void actualizarEstado(int idSolicitud, String nuevoEstado) {
        String sql = "UPDATE tb_solicitudes SET estado_tramite = ? WHERE id_solicitud = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idSolicitud);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar estado de solicitud", e);
        }
    }

    @Override
    public int contarPendientes() {
        String sql = "SELECT COUNT(*) FROM tb_solicitudes WHERE estado_tramite = 'Pendiente'";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new DAOException("Error al contar solicitudes pendientes", e);
        }
        return 0;
    }

    private Solicitud mapear(ResultSet rs) throws SQLException {
        Solicitud s = new Solicitud();
        s.setIdSolicitud(rs.getInt("id_solicitud"));
        s.setIdMascota(rs.getInt("id_mascota"));
        s.setIdCliente(rs.getInt("id_cliente"));
        s.setTipoSolicitud(rs.getString("tipo_solicitud"));
        s.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        s.setEstadoTramite(rs.getString("estado_tramite"));
        return s;
    }
    // ---> PEGA ESTE BLOQUE AL FINAL DE TU CLASE, ANTES DE LA ÚLTIMA LLAVE <---
    @Override
    public void rechazarPendientesPorMascota(int idMascota, String observacion) {
        String sql = "UPDATE tb_solicitudes SET estado_tramite = 'Rechazado', observacion = ? "
                   + "WHERE id_mascota = ? AND estado_tramite = 'Pendiente' AND tipo_solicitud = 'ADOPCION'";
                   
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, observacion);
            ps.setInt(2, idMascota);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al rechazar solicitudes pendientes", e);
        }
    }
}