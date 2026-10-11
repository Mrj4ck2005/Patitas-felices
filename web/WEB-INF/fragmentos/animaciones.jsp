<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 1. Overlay de Éxito (Mensajes dinámicos - Usa el GIF) -->
<c:if test="${not empty param.msg}">
    <c:set var="mensajeExito" value="" />
    <c:choose>
        <c:when test="${param.msg == 'solicitud_enviada'}">
            <c:set var="mensajeExito" value="¡Se envió la solicitud de registro correctamente!" />
        </c:when>
        <c:when test="${param.msg == 'adopcion_enviada'}">
            <c:set var="mensajeExito" value="¡Solicitud de adopción enviada con éxito!" />
        </c:when>
    </c:choose>

    <c:if test="${not empty mensajeExito}">
        <div id="overlayExito" style="position:fixed; top:0; left:0; width:100%; height:100%;
             background:rgba(255,255,255,0.95); z-index:9999; text-align:center;
             display:flex; align-items:center; justify-content:center; flex-direction:column;">
            
            <!-- Ruta corregida apuntando al GIF en la raíz pública -->
            <img src="${pageContext.request.contextPath}/recursos/animaciones/check-exito.gif"
                 style="width:150px; height:150px;" alt="Éxito">
                 
            <p class="fw-bold text-success mt-3 fs-5"><c:out value="${mensajeExito}"/></p>
            <p class="text-muted">Nos comunicaremos contigo muy pronto.</p>
        </div>
        <script>
            setTimeout(function () {
                var overlay = document.getElementById('overlayExito');
                if (overlay) overlay.style.display = 'none';
            }, 3000); // desaparece solo después de 3 segundos
        </script>
    </c:if>
</c:if>

<!-- 2. Overlay de Carga (Al enviar formularios - Usa tu SVG de perrito) -->
<div id="overlayCarga" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
     background:rgba(255,255,255,0.95); z-index:9998; text-align:center;
     align-items:center; justify-content:center; flex-direction:column;">
    
    <!-- Modificado: Se quitó el spinner clásico por tu animación SVG personalizada -->
    <img src="${pageContext.request.contextPath}/recursos/animaciones/perrito-cargando.svg"
         style="width:180px; height:180px;" alt="Cargando...">
         
    <p class="fw-bold text-info mt-3 fs-5">Enviando solicitud, por favor espera...</p>
</div>

<!-- 3. Script para activar la carga solo en formularios marcados -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Solo aplica a formularios que tengan data-loading="true"
        var forms = document.querySelectorAll('form[data-loading="true"]');
        forms.forEach(function(form) {
            form.addEventListener('submit', function() {
                // Solo muestra la carga si el formulario pasa la validación de Bootstrap
                if(form.checkValidity()) {
                    var overlayCarga = document.getElementById('overlayCarga');
                    if (overlayCarga) {
                        overlayCarga.style.display = 'flex';
                    }
                }
            });
        });
    });
</script>
