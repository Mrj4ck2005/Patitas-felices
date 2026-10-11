package com.patitasfelices.filtro;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/** Fuerza UTF-8 en todos los requests/responses (evita problemas con enye/tildes). */
@WebFilter("/*")
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }
}
