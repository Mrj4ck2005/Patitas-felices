<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/fragmentos/header.jsp" />
<jsp:include page="/WEB-INF/fragmentos/navbar.jsp" />

<div class="bg-dark text-white text-center py-5">
    <h1 class="fw-bold">Encuentra Tu Companero Ideal</h1>
    <p class="lead">Busca entre nuestros animales esperando un hogar</p>
</div>

<div class="container py-4">
  <!-- OJO: A este formulario de filtros NO se le pone data-loading para que no salga la animación azul al filtrar -->
  <form method="get" action="${pageContext.request.contextPath}/adoptar" class="row g-3 mb-4 bg-white p-3 rounded shadow-sm">
    <div class="col-md-4">
      <label class="form-label small text-muted">ESPECIE</label>
      <select name="especie" class="form-select">
        <option value="Todos">Todos</option>
        <option value="Perro" ${param.especie == 'Perro' ? 'selected' : ''}>Perro</option>
        <option value="Gato" ${param.especie == 'Gato' ? 'selected' : ''}>Gato</option>
      </select>
    </div>
    <div class="col-md-4">
      <label class="form-label small text-muted">RAZA</label>
      <!-- SEGURIDAD XSS: Se escapa lo que escribió el usuario en la caja de búsqueda -->
      <input type="text" name="raza" class="form-control" placeholder="Todas las Razas" value="<c:out value='${param.raza}'/>">
    </div>
    <div class="col-md-4 d-flex align-items-end">
      <button type="submit" class="btn btn-info text-white w-100">Filtrar</button>
    </div>
  </form>

  <p class="text-muted">${mascotas.size()} animales disponibles para adopcion</p>

  <div class="row g-4">
    <c:forEach var="m" items="${mascotas}">
      <div class="col-md-4">
        <div class="card h-100 shadow-sm">
          <img src="<c:out value='${m.fotoUrl}'/>" class="card-img-top" style="height:220px; object-fit:cover;" alt="<c:out value='${m.nombre}'/>">
          <div class="card-body">
            <span class="badge bg-info-subtle text-info mb-2"><c:out value='${m.especie}'/></span>
            <h5 class="card-title"><c:out value='${m.nombre}'/></h5>
            <p class="card-text text-muted small"><c:out value='${m.raza}'/> &middot; <c:out value='${m.edadMeses}'/> meses</p>
            <button class="btn btn-outline-info w-100" data-bs-toggle="modal" data-bs-target="#modalMascota${m.idMascota}">
                Ver Mas
            </button>
          </div>
        </div>
      </div>

      <!-- Modal de detalle + formulario de adopcion -->
      <div class="modal fade" id="modalMascota${m.idMascota}" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title"><c:out value='${m.nombre}'/></h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <img src="<c:out value='${m.fotoUrl}'/>" class="img-fluid rounded mb-3" alt="<c:out value='${m.nombre}'/>">
              <p><strong>Raza:</strong> <c:out value='${m.raza}'/></p>
              <p><strong>Edad:</strong> <c:out value='${m.edadMeses}'/> meses</p>
              <p><strong>Salud:</strong> <c:out value='${m.descripcionSalud}'/></p>

              <!-- OJO: Aquí SÍ ponemos data-loading="true" para que aparezca el overlay azul -->
              <form method="post" action="${pageContext.request.contextPath}/solicitar-adopcion" data-loading="true">
                <input type="hidden" name="idMascota" value="${m.idMascota}">
                <div class="mb-2">
                    <label class="form-label small">DNI</label>
                    <input type="text" name="numeroDocumento" class="form-control" required>
                </div>
                <div class="mb-2">
                    <label class="form-label small">Nombre completo</label>
                    <input type="text" name="nombreCompleto" class="form-control" required>
                </div>
                <div class="mb-2">
                    <label class="form-label small">Correo</label>
                    <input type="email" name="correo" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label small">Telefono</label>
                    <input type="text" name="telefono" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-warning w-100">Quiero Adoptar</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<!-- Incluimos la lógica de las animaciones (Éxito y Carga) -->
<jsp:include page="/WEB-INF/fragmentos/animaciones.jsp" />

<jsp:include page="/WEB-INF/fragmentos/footer.jsp" />