package com.patitasfelices.dao.impl;

import com.patitasfelices.config.Conexion;
import com.patitasfelices.dao.interfaz.IUsuarioDAO;
import com.patitasfelices.excepciones.DAOException;
import com.patitasfelices.modelo.Usuario;

import java.sql.*;

public class UsuarioDAOImpl implements IUsuarioDAO {

    @Override
    public Usuario buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM tb_administradores WHERE usuario = ? AND estado = 'Activo'";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_admin"));
                    u.setNombreCompleto(rs.getString("nombre_completo"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setPasswordHash(rs.getString("clave"));
                    u.setRol(rs.getString("rol"));
                    u.setEstado("Activo".equalsIgnoreCase(rs.getString("estado")));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al buscar usuario", e);
        }
        return null;
    }
}