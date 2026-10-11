package com.patitasfelices.controlador;

import com.patitasfelices.modelo.Usuario;
import com.patitasfelices.servicio.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/login", "/logout"})
public class AuthServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Bloque de Logout Mejorado
        if (req.getServletPath().equals("/logout")) {
            HttpSession session = req.getSession(false); // No crea una sesión nueva si ya expiró
            if (session != null) {
                session.invalidate(); // Destruye la sesión por completo
            }
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        req.getRequestDispatcher("/WEB-INF/vistas/admin/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String usuario = req.getParameter("usuario");
        String password = req.getParameter("password");

        Usuario u = authService.autenticar(usuario, password);
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("usuario", u);
            session.setMaxInactiveInterval(30 * 60); // 30 minutos
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Usuario o contrasena incorrectos");
            req.getRequestDispatcher("/WEB-INF/vistas/admin/login.jsp").forward(req, resp);
        }
    }
}