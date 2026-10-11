package com.patitasfelices.dao.impl;

import com.patitasfelices.config.Conexion;
import com.patitasfelices.dao.interfaz.IClienteDAO;
import com.patitasfelices.excepciones.DAOException;
import com.patitasfelices.modelo.Cliente;

import java.sql.*;

public class ClienteDAOImpl implements IClienteDAO {

    @Override
    public int registrar(Cliente c) {
        String sql = "INSERT INTO tb_clientes (tipo_documento, numero_documento, nombre_completo, "
                + "telefono, correo, tipo_cliente) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getTipoDocumento());
            ps.setString(2, c.getNumeroDocumento());
            ps.setString(3, c.getNombreCompleto());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setString(6, c.getTipoCliente());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al registrar cliente", e);
        }
        return -1;
    }

    @Override
    public Cliente buscarPorDocumento(String tipoDoc, String numDoc) {
        String sql = "SELECT * FROM tb_clientes WHERE tipo_documento = ? AND numero_documento = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoDoc);
            ps.setString(2, numDoc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar cliente por documento", e);
        }
        return null;
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM tb_clientes WHERE id_cliente = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar cliente por id", e);
        }
        return null;
    }

    @Override
    public void actualizar(Cliente c) {
        String sql = "UPDATE tb_clientes SET tipo_documento=?, numero_documento=?, nombre_completo=?, "
                + "telefono=?, correo=? WHERE id_cliente=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getTipoDocumento());
            ps.setString(2, c.getNumeroDocumento());
            ps.setString(3, c.getNombreCompleto());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getCorreo());
            ps.setInt(6, c.getIdCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar cliente", e);
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setTipoDocumento(rs.getString("tipo_documento"));
        c.setNumeroDocumento(rs.getString("numero_documento"));
        c.setNombreCompleto(rs.getString("nombre_completo"));
        c.setTelefono(rs.getString("telefono"));
        c.setCorreo(rs.getString("correo"));
        c.setTipoCliente(rs.getString("tipo_cliente"));
        return c;
    }
}//actualizado de la version 2 metod actualizar
