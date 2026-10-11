<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- =========================================================
     ESTILOS RESPONSIVE DEL PANEL ADMIN
     ========================================================= -->
<style>
    /* Layout general */
    .admin-layout {
        display: flex;
        width: 100%;
        min-height: 100vh;
    }
    /* Sidebar */
    .admin-sidebar {
        width: 250px;
        min-height: 100vh;
        flex-shrink: 0;
    }
    /* Contenido */
    .admin-content {
        min-width: 0;
        flex: 1;
        overflow-x: hidden;
    }
    /* Tablas */
    .admin-table {
        min-width: 650px;
    }
    /* Modales */
    .modal img {
        max-width: 100%;
    }
    /* Al usar el menu lateral, la pagina baja suavemente hasta la seccion */
    html {
        scroll-behavior: smooth;
    }
    /* =====================================================
   ESCRITORIO: sidebar fijo (no se mueve con el scroll)
   ===================================================== */
    @media (min-width: 768px) {
        .admin-sidebar {
            position: sticky;
            top: 0;
            height: 100vh;
            align-self: flex-start;
            overflow-y: auto;
        }
    }
    /* =====================================================
       CELULARES Y TABLETS PEQUEÑAS
       ===================================================== */
    @media (max-width: 767px) {
        .admin-layout {
            flex-direction: column;
        }
        .admin-sidebar {
            width: 100% !important;
            min-height: auto !important;
        }
        .admin-sidebar h5 {
            margin-bottom: 1rem !important;
        }
        .admin-sidebar .nav {
            flex-direction: row;
            flex-wrap: wrap;
            gap: 5px;
        }
        .admin-sidebar .nav-item {
            margin-bottom: 0 !important;
        }
        .admin-sidebar .nav-link {
            font-size: 14px;
        }
        .admin-sidebar .btn {
            margin-top: 15px !important;
        }
        .admin-content {
            width: 100%;
            padding: 1rem !important;
        }
        .admin-content h4 {
            font-size: 1.25rem;
        }
        /* Modal en celular */
        .modal-dialog {
            margin: 0.5rem;
        }
        .modal-body {
            max-height: 75vh;
            overflow-y: auto;
        }
    }
    /* =====================================================
       CELULARES MUY PEQUEÑOS
       ===================================================== */
    @media (max-width: 400px) {
        .admin-content {
            padding: 0.75rem !important;
        }
        .admin-sidebar {
            padding: 1rem !important;
        }
        .admin-sidebar h5 {
            font-size: 1rem;
        }
        .admin-sidebar .nav-link {
            font-size: 13px;
            padding: 6px 8px !important;
        }
        .admin-stats .card {
            padding: 0.75rem !important;
        }
        .admin-stats .fs-3 {
            font-size: 1.4rem !important;
        }
        .table-responsive {
            padding: 0.75rem !important;
        }
        .btn {
            font-size: 13px;
        }
    }
</style>
<jsp:include page="/WEB-INF/fragmentos/header.jsp" />
<!-- =========================================================
     LAYOUT PRINCIPAL
     ========================================================= -->
<div class="admin-layout">
    <!-- =====================================================
         SIDEBAR
         ===================================================== -->
    <nav class="bg-dark text-white p-3 admin-sidebar">
        <h5 class="fw-bold mb-4">
            🐾 Patitas Felices<br>
            <small class="text-white-50">Panel Admin</small>
        </h5>
        <ul class="nav flex-column">
            <li class="nav-item mb-2">
                <a class="nav-link text-white active bg-info rounded px-2"
                   href="#registros">
                    Solicitudes
                </a>
            </li>
            <li class="nav-item mb-2">
                <a class="nav-link text-white-50 px-2"
                   href="#adopciones">
                    Historial de Adopciones
                </a>
            </li>
        </ul>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-outline-light btn-sm mt-5">
            Salir al Sitio
        </a>
    </nav>
    <!-- =====================================================
         CONTENIDO PRINCIPAL
         ===================================================== -->
    <div class="admin-content p-4">
        <h4 class="fw-bold">
            Panel de administracion
        </h4>
        <p class="text-muted mb-4">
            Resumen general del refugio
        </p>
        <!-- =================================================
             TARJETAS DE ESTADÍSTICAS (3, ancho completo)
             ================================================= -->
        <div class="row g-3 mb-5 admin-stats">
            <!-- Disponibles -->
            <div class="col-4">
                <div class="card border-0 shadow-sm text-center p-3 h-100">
                    <div class="fs-3 fw-bold text-info">
                        ${totalDisponibles}
                    </div>
                    <div class="text-muted small">
                        Disponibles
                    </div>
                </div>
            </div>
            <!-- Adoptadas -->
            <div class="col-4">
                <div class="card border-0 shadow-sm text-center p-3 h-100">
                    <div class="fs-3 fw-bold text-success">
                        ${totalAdoptadas}
                    </div>
                    <div class="text-muted small">
                        Adoptadas
                    </div>
                </div>
            </div>
            <!-- Tramites pendientes -->
            <div class="col-4">
                <div class="card border-0 shadow-sm text-center p-3 h-100">
                    <div class="fs-3 fw-bold text-danger">
                        ${totalSolicitudesPendientes}
                    </div>
                    <div class="text-muted small">
                        Trámites Pendientes
                    </div>
                </div>
            </div>
        </div>
        <!-- =================================================
             SOLICITUDES DE REGISTRO
             ================================================= -->
        <h4 class="fw-bold" id="registros">
            Solicitudes de Registro
        </h4>
        <div class="table-responsive bg-white rounded shadow-sm p-3 mb-5">
            <table class="table align-middle admin-table">
                <thead>
                    <tr>
                        <th>Solicitante</th>
                        <th>Mascota</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${registros}">
                        <tr>
                            <!-- Solicitante -->
                            <td>
                                ${s.nombreCompleto}
                            </td>
                            <!-- Mascota -->
                            <td>
                                ${s.nombreMascota}
                            </td>
                            <!-- Fecha -->
                            <td>
                                ${s.fechaRegistro}
                            </td>
                            <!-- Estado -->
                            <td>
                                <c:choose>
                                    <c:when test="${s.estadoTramite == 'Pendiente'}">
                                        <span class="badge bg-warning text-dark">
                                            Pendiente
                                        </span>
                                    </c:when>
                                    <c:when test="${s.estadoTramite == 'Aprobado'}">
                                        <span class="badge bg-success">
                                            Aprobado
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">
                                            Rechazado
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                                <!-- Si la mascota ya fue adoptada, se indica sutilmente -->
                                <c:if test="${s.estadoMascota == 'Adoptado'}">
                                    <div class="small text-muted fst-italic">(Adoptado)</div>
                                </c:if>
                            </td>
                            <!-- Acciones -->
                            <td>
                                <!-- Aprobar / Rechazar -->
                                <c:if test="${s.estadoTramite == 'Pendiente'}">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/dashboard/aprobar-registro"
                                          class="d-inline">
                                        <input type="hidden"
                                               name="idSolicitud"
                                               value="${s.idSolicitud}">
                                        <input type="hidden"
                                               name="idMascota"
                                               value="${s.idMascota}">
                                        <button type="submit"
                                                class="btn btn-sm btn-success">
                                            Aprobar
                                        </button>
                                    </form>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/dashboard/rechazar"
                                          class="d-inline">
                                        <input type="hidden"
                                               name="idSolicitud"
                                               value="${s.idSolicitud}">
                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger">
                                            Rechazar
                                        </button>
                                    </form>
                                </c:if>
                                <!-- Editar -->
                                <c:if test="${s.estadoTramite != 'Rechazado'
                                              && s.estadoMascota != 'Adoptado'}">
                                      <button type="button"
                                              class="btn btn-sm btn-outline-info"
                                              data-bs-toggle="modal"
                                              data-bs-target="#modalRegistro${s.idSolicitud}">
                                          Editar
                                      </button>
                                </c:if>
                                <!-- Ver mascota adoptada -->
                                <c:if test="${s.estadoMascota == 'Adoptado'}">
                                    <button type="button"
                                            class="btn btn-sm btn-outline-secondary"
                                            data-bs-toggle="modal"
                                            data-bs-target="#modalRegistro${s.idSolicitud}">
                                        Ver
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                        <!-- =================================================
                             MODAL REGISTRO / EDITAR / VER
                             ================================================= -->
                    <div class="modal fade"
                         id="modalRegistro${s.idSolicitud}"
                         tabindex="-1"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-lg">
                            <div class="modal-content">
                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <c:choose>
                                            <c:when test="${s.estadoMascota == 'Adoptado'}">
                                                Detalle de ${s.nombreMascota} (ya adoptada)
                                            </c:when>
                                            <c:otherwise>
                                                Editar solicitud de ${s.nombreMascota}
                                            </c:otherwise>
                                        </c:choose>
                                    </h5>
                                    <button type="button"
                                            class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Cerrar">
                                    </button>
                                </div>
                                <!-- Formulario -->
                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/mascota"
                                      enctype="multipart/form-data">
                                    <div class="modal-body">
                                        <!-- Datos ocultos -->
                                        <input type="hidden"
                                               name="idSolicitud"
                                               value="${s.idSolicitud}">
                                        <input type="hidden"
                                               name="idMascota"
                                               value="${s.idMascota}">
                                        <input type="hidden"
                                               name="idCliente"
                                               value="${s.idCliente}">
                                        <input type="hidden"
                                               name="fotoActual"
                                               value="${s.fotoUrl}">
                                        <input type="hidden"
                                               name="accion"
                                               value="${s.estadoTramite == 'Aprobado'
                                                        ? 'guardarCambios'
                                                        : 'guardarYaprobar'}">
                                        <!-- =============================
                                             MASCOTA
                                             ============================= -->
                                        <div class="row g-3">
                                            <!-- Foto -->
                                            <div class="col-md-5 text-center">
                                                <c:if test="${not empty s.fotoUrl}">
                                                    <img src="${s.fotoUrl}"
                                                         class="img-fluid rounded mb-2"
                                                         style="max-height:200px; object-fit:cover;"
                                                         alt="${s.nombreMascota}">
                                                </c:if>
                                                <c:if test="${s.estadoMascota != 'Adoptado'}">
                                                    <label class="form-label small d-block">
                                                        Reemplazar foto
                                                    </label>
                                                    <input type="file"
                                                           name="foto"
                                                           class="form-control form-control-sm"
                                                           accept="image/*">
                                                </c:if>
                                            </div>
                                            <!-- Datos -->
                                            <div class="col-md-7">
                                                <h6 class="text-muted small text-uppercase">
                                                    Datos de la mascota
                                                </h6>
                                                <!-- Nombre -->
                                                <div class="mb-2">
                                                    <label class="form-label small">
                                                        Nombre
                                                    </label>
                                                    <input type="text"
                                                           name="nombreMascota"
                                                           class="form-control"
                                                           value="${s.nombreMascota}"
                                                           ${s.estadoMascota == 'Adoptado' ? 'readonly' : ''}
                                                           required>
                                                </div>
                                                <!-- Especie / Raza -->
                                                <div class="row g-2 mb-2">
                                                    <div class="col-6">
                                                        <label class="form-label small">
                                                            Especie
                                                        </label>
                                                        <input type="text"
                                                               name="especie"
                                                               class="form-control"
                                                               value="${s.especie}"
                                                               ${s.estadoMascota == 'Adoptado' ? 'readonly' : ''}
                                                               required>
                                                    </div>
                                                    <div class="col-6">
                                                        <label class="form-label small">
                                                            Raza
                                                        </label>
                                                        <input type="text"
                                                               name="raza"
                                                               class="form-control"
                                                               value="${s.raza}"
                                                               ${s.estadoMascota == 'Adoptado' ? 'readonly' : ''}>
                                                    </div>
                                                </div>
                                                <!-- Edad -->
                                                <div class="mb-2">
                                                    <label class="form-label small">
                                                        Edad (meses)
                                                    </label>
                                                    <input type="number"
                                                           name="edadMeses"
                                                           class="form-control"
                                                           value="${s.edadMeses}"
                                                           min="0"
                                                           ${s.estadoMascota == 'Adoptado' ? 'readonly' : ''}
                                                           required>
                                                </div>
                                                <!-- Salud -->
                                                <div class="mb-2">
                                                    <label class="form-label small">
                                                        Descripcion de salud
                                                    </label>
                                                    <textarea name="descripcionSalud"
                                                              class="form-control"
                                                              rows="2"
                                                              ${s.estadoMascota == 'Adoptado' ? 'readonly' : ''}>${s.descripcionSalud}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                        <!-- =============================
                                             DATOS DEL SOLICITANTE
                                             ============================= -->
                                        <h6 class="text-muted small text-uppercase">
                                            Datos del solicitante
                                            <c:if test="${s.estadoTramite == 'Aprobado'
                                                          || s.estadoMascota == 'Adoptado'}">
                                                  <span class="text-danger">
                                                      (solo lectura)
                                                  </span>
                                            </c:if>
                                        </h6>
                                        <div class="row g-2">
                                            <!-- Tipo documento -->
                                            <div class="col-md-3">
                                                <label class="form-label small">
                                                    Tipo Doc.
                                                </label>
                                                <input type="text"
                                                       name="tipoDocumento"
                                                       class="form-control"
                                                       value="${s.tipoDocumento}"
                                                       ${s.estadoTramite == 'Aprobado'
                                                         || s.estadoMascota == 'Adoptado'
                                                         ? 'readonly'
                                                         : ''}>
                                            </div>
                                            <!-- Documento -->
                                            <div class="col-md-3">
                                                <label class="form-label small">
                                                    N° Documento
                                                </label>
                                                <input type="text"
                                                       name="numeroDocumento"
                                                       class="form-control"
                                                       value="${s.numeroDocumento}"
                                                       ${s.estadoTramite == 'Aprobado'
                                                         || s.estadoMascota == 'Adoptado'
                                                         ? 'readonly'
                                                         : ''}>
                                            </div>
                                            <!-- Nombre completo -->
                                            <div class="col-md-6">
                                                <label class="form-label small">
                                                    Nombre completo
                                                </label>
                                                <input type="text"
                                                       name="nombreCompleto"
                                                       class="form-control"
                                                       value="${s.nombreCompleto}"
                                                       ${s.estadoTramite == 'Aprobado'
                                                         || s.estadoMascota == 'Adoptado'
                                                         ? 'readonly'
                                                         : ''}>
                                            </div>
                                            <!-- Teléfono -->
                                            <div class="col-md-6">
                                                <label class="form-label small">
                                                    Telefono
                                                </label>
                                                <input type="text"
                                                       name="telefono"
                                                       class="form-control"
                                                       value="${s.telefono}"
                                                       ${s.estadoTramite == 'Aprobado'
                                                         || s.estadoMascota == 'Adoptado'
                                                         ? 'readonly'
                                                         : ''}>
                                            </div>
                                            <!-- Correo -->
                                            <div class="col-md-6">
                                                <label class="form-label small">
                                                    Correo
                                                </label>
                                                <input type="email"
                                                       name="correo"
                                                       class="form-control"
                                                       value="${s.correo}"
                                                       ${s.estadoTramite == 'Aprobado'
                                                         || s.estadoMascota == 'Adoptado'
                                                         ? 'readonly'
                                                         : ''}>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Modal Footer -->
                                    <div class="modal-footer">
                                        <button type="button"
                                                class="btn btn-outline-secondary"
                                                data-bs-dismiss="modal">
                                            Cerrar
                                        </button>
                                        <c:if test="${s.estadoMascota != 'Adoptado'}">
                                            <button type="submit"
                                                    class="btn btn-success">
                                                ${s.estadoTramite == 'Aprobado'
                                                  ? 'Guardar'
                                                  : 'Aprobar'}
                                            </button>
                                        </c:if>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- =================================================
             HISTORIAL DE ADOPCIONES
             ================================================= -->
        <h4 class="fw-bold" id="adopciones">
            Historial de Adopciones
        </h4>
        <p class="text-muted small mb-3">
            Las solicitudes <strong>Pendientes</strong> esperan tu decision;
            las aprobadas figuran como <strong>Concretado</strong> (mascota entregada).
        </p>
        <div class="table-responsive bg-white rounded shadow-sm p-3">
            <table class="table align-middle admin-table">
                <thead>
                    <tr>
                        <th>Adoptante</th>
                        <th>Mascota</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${adopciones}">
                        <tr>
                            <!-- Adoptante -->
                            <td>
                                ${a.nombreCompleto}
                            </td>
                            <!-- Mascota -->
                            <td>
                                ${a.nombreMascota}
                            </td>
                            <!-- Fecha -->
                            <td>
                                ${a.fechaRegistro}
                            </td>
                            <!-- Estado -->
                            <td>
                                <c:choose>
                                    <c:when test="${a.estadoTramite == 'Pendiente'}">
                                        <span class="badge bg-warning text-dark">
                                            Pendiente
                                        </span>
                                    </c:when>
                                    <c:when test="${a.estadoTramite == 'Aprobado'}">
                                        <span class="badge bg-success">
                                            Concretado
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">
                                            Rechazado
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <!-- Acciones -->
                            <td>
                                <c:if test="${a.estadoTramite == 'Pendiente'}">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/dashboard/aprobar-adopcion"
                                          class="d-inline">
                                        <input type="hidden"
                                               name="idSolicitud"
                                               value="${a.idSolicitud}">
                                        <input type="hidden"
                                               name="idMascota"
                                               value="${a.idMascota}">
                                        <button type="submit"
                                                class="btn btn-sm btn-success">
                                            Aprobar
                                        </button>
                                    </form>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/dashboard/rechazar"
                                          class="d-inline">
                                        <input type="hidden"
                                               name="idSolicitud"
                                               value="${a.idSolicitud}">
                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger">
                                            Rechazar
                                        </button>
                                    </form>
                                </c:if>
                                <button type="button"
                                        class="btn btn-sm btn-outline-info"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalAdopcion${a.idSolicitud}">
                                    Ver
                                </button>
                            </td>
                        </tr>
                        <!-- =================================================
                             MODAL VER ADOPCIÓN
                             ================================================= -->
                    <div class="modal fade"
                         id="modalAdopcion${a.idSolicitud}"
                         tabindex="-1"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        Solicitud de adopcion:
                                        ${a.nombreMascota}
                                    </h5>
                                    <button type="button"
                                            class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Cerrar">
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <c:if test="${not empty a.fotoUrl}">
                                        <img src="${a.fotoUrl}"
                                             class="img-fluid rounded mb-3"
                                             style="max-height:220px; width:100%; object-fit:cover;"
                                             alt="${a.nombreMascota}">
                                    </c:if>
                                    <p>
                                        <strong>Mascota:</strong>
                                        ${a.nombreMascota}
                                        (${a.especie} - ${a.raza})
                                    </p>
                                    <p>
                                        <strong>Edad:</strong>
                                        ${a.edadMeses} meses
                                    </p>
                                    <p>
                                        <strong>Salud:</strong>
                                        ${a.descripcionSalud}
                                    </p>
                                    <hr>
                                    <p>
                                        <strong>Adoptante:</strong>
                                        ${a.nombreCompleto}
                                    </p>
                                    <p>
                                        <strong>Documento:</strong>
                                        ${a.tipoDocumento}
                                        ${a.numeroDocumento}
                                    </p>
                                    <p>
                                        <strong>Telefono:</strong>
                                        ${a.telefono}
                                    </p>
                                    <p>
                                        <strong>Correo:</strong>
                                        ${a.correo}
                                    </p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button"
                                            class="btn btn-outline-secondary"
                                            data-bs-dismiss="modal">
                                        Cerrar
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/fragmentos/footer.jsp" />