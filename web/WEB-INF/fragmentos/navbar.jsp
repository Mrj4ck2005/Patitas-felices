<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm sticky-top">
  <div class="container">
    <a class="navbar-brand fw-bold text-info" href="${pageContext.request.contextPath}/index.jsp">
        🐾 Refugio Patitas Felices
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navMenu">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Inicio</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/adoptar">Adoptar</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/dar-adopcion">Dar en Adopcion</a></li>
        </ul>
        <!-- El botón de Iniciar Sesión ha sido eliminado para ocultar el acceso administrativo al público -->
    </div>
  </div>
</nav>