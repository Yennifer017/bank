<%-- 
    Document   : gestionCajeros
    Created on : Sep 16, 2023, 2:19:50 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion de Usuarios</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <h3 class="titulo text-center">${titulo}</h3>
        <div class="contenido">
            <c:forEach items="${consultas}" var="consulta">
                <div class="m-3 row">
                    <div class="col-md-12 card">
                        <div class="card-body">
                            <h5 class="card-title">nombre: ${consulta.name}</h5>
                            <p class="card-text">No de identificacion: ${consulta.noIdentificacion}</p>
                            <p class="card-text">Direccion: ${consulta.address}</p>
                            <p class="card-text">Sexo: ${consulta.sexo}</p>
                            <form action="${url}" method="POST">
                                <input name="idCurrentUser" type="hidden" value="${consulta.id}"> 
                                <button type="submit"
                                    class="btn btn-primary">Editar informacion</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
