<%-- 
    Document   : detailsAcounts
    Created on : Sep 18, 2023, 5:40:56 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles de cuentas asociadas</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <h3 class="titulo text-center">Estados de cuenta asociados al usuario   </h3>
        <div class="contenido">
            <c:forEach items="${cuentas}" var="cuenta">
                <div class="m-3 row">
                    <div class="col-md-12 card">
                        <div class="card-body">
                            <h5 class="card-title">Codigo de cuenta: ${cuenta.id}</h5>
                            <p class="card-text">Saldo en cuenta: ${cuenta.saldo}</p>
                            <p class="card-text">Fecha de creacion: ${cuenta.fechaCreacion}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
