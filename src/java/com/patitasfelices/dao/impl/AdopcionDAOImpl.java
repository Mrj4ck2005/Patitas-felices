package com.patitasfelices.dao.impl;

import com.patitasfelices.config.Conexion;
import com.patitasfelices.dao.interfaz.IAdopcionDAO;
import com.patitasfelices.excepciones.DAOException;
import com.patitasfelices.modelo.Adopcion;

import java.sql.*;

public class AdopcionDAOImpl implements IAdopcionDAO {

    @Override
    public int registrar(Adopcion a) {
        // tb_adopciones solo tiene id_solicitud como FK; id_mascota e id_cliente
        // se obtienen indirectamente a traves de la solicitud, por eso no se insertan aqui.
        String sql = "INSERT INTO tb_adopciones (id_solicitud, estado_adopcion) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getIdSolicitud());
            ps.setString(2, a.getEstadoAdopcion());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            throw new DAOException("No se pudo obtener el id de la adopcion generada", null);
        } catch (SQLException e) {
            throw new DAOException("Error al registrar adopcion", e);
        }
    }
}  
//nuevo archivo creado recientemente 
