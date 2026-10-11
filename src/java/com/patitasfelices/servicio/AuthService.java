package com.patitasfelices.servicio;

import com.patitasfelices.dao.impl.UsuarioDAOImpl;
import com.patitasfelices.dao.interfaz.IUsuarioDAO;
import com.patitasfelices.modelo.Usuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Requiere la libreria jbcrypt (org.mindrot:jbcrypt) para el hash de passwords.
 */
public class AuthService {

    private final IUsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    public Usuario autenticar(String usuario, String passwordPlano) {
        Usuario u = usuarioDAO.buscarPorUsuario(usuario);
        if (u == null) return null;
        boolean coincide = BCrypt.checkpw(passwordPlano, u.getPasswordHash());
        return coincide ? u : null;
    }
}
