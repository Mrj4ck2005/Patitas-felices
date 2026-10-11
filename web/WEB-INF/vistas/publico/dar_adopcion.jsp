<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="/WEB-INF/fragmentos/header.jsp" />
<jsp:include page="/WEB-INF/fragmentos/navbar.jsp" />

<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="fw-bold mb-4 text-info">Formulario de Registro</h3>

          <form method="post" action="${pageContext.request.contextPath}/dar-adopcion" enctype="multipart/form-data">

            <h6 class="text-muted">1. Datos de la Mascota</h6>
            <div class="row g-3 mb-4">
              <div class="col-md-6">
                <label class="form-label">Especie *</label>
                <select name="especie" class="form-select" required>
                  <option value="">Selecciona una especie</option>
                  <option value="Perro">Perro</option>
                  <option value="Gato">Gato</option>
                </select>
              </div>
              <div class="col-md-6">
                <label class="form-label">
                  Raza *
                  <span class="badge rounded-pill bg-secondary ms-1" style="cursor:help;"
                        data-bs-toggle="tooltip" data-bs-placement="top"
                        title="Si no sabe la raza exacta, escriba 'Mestizo' u 'Otro'">?</span>
                </label>
                <input type="text" name="raza" class="form-control" placeholder="Ej. Labrador, Mestizo" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">
                  Nombre de la Mascota *
                  <span class="badge rounded-pill bg-secondary ms-1" style="cursor:help;"
                        data-bs-toggle="tooltip" data-bs-placement="top"
                        title="Si no sabe el nombre, puede poner uno descriptivo, ej. 'Negrito'">?</span>
                </label>
                <input type="text" name="nombreMascota" class="form-control" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">
                  Edad (en meses) *
                  <span class="badge rounded-pill bg-secondary ms-1" style="cursor:help;"
                        data-bs-toggle="tooltip" data-bs-placement="top"
                        title="Si no sabe la edad exacta, ponga una edad aproximada">?</span>
                </label>
                <input type="number" name="edadMeses" class="form-control" min="0" required>
              </div>
              <div class="col-12">
                <label class="form-label">
                  Descripcion de Salud y Personalidad *
                  <span class="badge rounded-pill bg-secondary ms-1" style="cursor:help;"
                        data-bs-toggle="tooltip" data-bs-placement="top"
                        title="Mencione rasgos que conozca: es amigable, es tercos, tiene alguna lesion o malestar, es agresivo,etc, tambien puede mencionar el motivo de darlo en adopcion.">?</span>
                </label>
                <textarea name="descripcionSalud" class="form-control" rows="3"
                    placeholder="Confirmen sobre condicion, salud, vacunas, etc."></textarea>
              </div>
              <div class="col-12">
                <label class="form-label">Foto de la Mascota</label>
                <input type="file" name="foto" class="form-control" accept="image/*">
                <small class="text-muted">Formatos JPG, PNG (max. 5MB)</small>
              </div>
            </div>

            <h6 class="text-muted">2. Datos del Propietario</h6>
            <div class="row g-3 mb-4">
              <div class="col-md-6">
                <label class="form-label">Nombre Completo *</label>
                <input type="text" name="nombreCompleto" class="form-control" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">Telefono de Contacto *</label>
                <input type="text" name="telefono" class="form-control" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">Numero de Documento *</label>
                <input type="text" name="numeroDocumento" class="form-control" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">Correo Electronico *</label>
                <input type="email" name="correo" class="form-control" required>
              </div>
            </div>

            <button type="submit" class="btn btn-info text-white w-100 py-2">Enviar Solicitud</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
  // Activa los tooltips de Bootstrap en esta pagina (los "?" flotantes)
  document.addEventListener('DOMContentLoaded', function () {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.forEach(function (el) {
      new bootstrap.Tooltip(el);
    });
  });
</script>

<!-- Overlay de carga -->
<div id="overlayCarga" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
     background:rgba(255,255,255,0.95); z-index:9999; text-align:center;
     align-items:center; justify-content:center; flex-direction:column;">
  <img src="${pageContext.request.contextPath}/recursos/animaciones/perrito-cargando.svg"
       style="width:200px; height:200px;" alt="Cargando">
  <p class="fw-bold text-info mt-3">Enviando...</p>
</div>

<script>
  document.querySelector('form').addEventListener('submit', function () {
    var overlay = document.getElementById('overlayCarga');
    overlay.style.display = 'flex';
  });
</script>

<jsp:include page="/WEB-INF/fragmentos/footer.jsp" />
