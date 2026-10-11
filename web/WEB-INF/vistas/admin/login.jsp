<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="/WEB-INF/fragmentos/header.jsp" />

<div class="d-flex align-items-center justify-content-center bg-light" style="min-height:100vh;">
  <div class="card shadow-sm" style="width:380px;">
    <div class="card-body p-4">
      <h4 class="text-center fw-bold text-info mb-4">🐾 Patitas Felices <br><small class="text-muted fs-6">Panel Admin</small></h4>

      <c:if test="${not empty error}">
        <div class="alert alert-danger py-2">${error}</div>
      </c:if>

      <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="mb-3">
          <label class="form-label">Usuario</label>
          <input type="text" name="usuario" class="form-control" required autofocus>
        </div>
        <div class="mb-3">
          <label class="form-label">Contrasena</label>
          <input type="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-info text-white w-100">Ingresar</button>
      </form>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/fragmentos/footer.jsp" />
