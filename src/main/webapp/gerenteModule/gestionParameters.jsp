<%-- 
    Document   : gestionParameters
    Created on : Sep 16, 2023, 12:19:49 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion Parameters</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <div class="contenido">
            <h3 class="titulo mb-5"> GESTION DE PARAMETROS DEL SISTEMA</h3>
            <c:forEach items="${limites}" var="limit">
                <form action="${pageContext.request.contextPath}/SystemParam" 
                      method="POST">
                    <input name="idParam" type="hidden" value="${limit.id}"> 
                    <div class="row m-3 bg-gray p-3">
                        <div class="col-8">
                            <h4>${limit.name}</h4>
                        </div>
                        <div class="col-3">
                            <input name="valueInput" 
                                   id="input"
                                   class="form-control" 
                                   type="text" 
                                   placeholder="${limit.value}" >
                        </div>  
                        <div class="col-1">
                            <button type="submit"
                                    class="btn btn-success">Cambiar</button>
                        </div>
                    </div>
                </form>
            </c:forEach>

            <c:if test="${!empty(updateError)}">
                <div class=" alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>No se ha actualizado - </strong> ${updateError}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${!empty(exito)}">
                <div class=" alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Exito - </strong> ${exito}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
        </div>

        <jsp:include page="/includes/footer.jsp"/> 
    </body>
</html>
