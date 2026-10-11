<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/fragmentos/header.jsp" />
<jsp:include page="/WEB-INF/fragmentos/navbar.jsp" />

<div class="bg-light py-5">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-md-6">
        <span class="badge bg-info-subtle text-info mb-3">🐾 Adopcion Responsable</span>
        <h1 class="display-5 fw-bold">Encuentra a tu nuevo <span class="text-info">mejor amigo</span> en Refugio Patitas Felices</h1>
        <p class="lead text-muted">Cientos de mascotas esperan un hogar amoroso. Dale una segunda oportunidad a quien mas lo necesita.</p>
        <a href="${pageContext.request.contextPath}/adoptar" class="btn btn-info btn-lg text-white me-2">Conocer a Nuestros Residentes</a><br/>
        <a href="${pageContext.request.contextPath}/dar-adopcion" class="btn btn-outline-secondary btn-lg mt-2">Dar en Adopcion</a>

        <div class="row mt-4 text-center">
          <div class="col-4"><h3 class="fw-bold">120+</h3><small class="text-muted">Animales rescatados</small></div>
          <div class="col-4"><h3 class="fw-bold">85+</h3><small class="text-muted">Adoptados este ano</small></div>
          <div class="col-4"><h3 class="fw-bold">50+</h3><small class="text-muted">Familias felices</small></div>
        </div>
      </div>
      <div class="col-md-6 mt-4 mt-md-0">
        <img src="https://res.cloudinary.com/dxe9gutzl/image/upload/v1789784741/ChatGPT_Image_18_sept_2026_21_22_47.png" class="img-fluid rounded-4 shadow" alt="Mascota feliz">
      </div>
    </div>
  </div>
</div>

<!-- Incluimos el fragmento de animaciones (Éxito y Carga) -->
<jsp:include page="/WEB-INF/fragmentos/animaciones.jsp" />

<jsp:include page="/WEB-INF/fragmentos/footer.jsp" />