<%-- 
    Document   : Transferencia
    Created on : Sep 19, 2023, 10:23:20 AM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transferencia</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <div class="card-body py-5 px-md-5">

            <div class="row d-flex justify-content-center">
                <div class="col-lg-8">
                    <h2 class="fw-bold my-5">Transferencias ${title}</h2>
                    <form action="${url}" method="POST">
                        <!-- 2 column grid layout with text inputs -->
                        <div class="row">
                            <div class="col-md-6 mb-4">
                                <div class="form-outline">
                                    <select name="idOriginAcount" class="form-select mb-4" aria-label="Default select example">
                                        <option selected>De la cuenta no...</option>
                                        <c:forEach items="${acountsUser}" var="acount">
                                            <!-- inicio de la busqueda de turnos disponibles -->
                                            <option value="${acount.id}">${acount.id} ---- saldo: ${acount.saldo}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <!-- identifier Input -->
                            <div class="col-md-6 mb-4">
                                <div class="form-outline">
                                    <select name="idToAcount" class="form-select mb-4" aria-label="Default select example">
                                        <option selected>A la cuenta no... </option>
                                        <c:forEach items="${acountsTo}" var="acount">
                                            <!-- inicio de la busqueda de turnos disponibles -->
                                            <option value="${acount.id}">${acount.id} ---- propietario: ${acount.propietario}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>


                        <!-- 2 column grid layout with text inputs -->
                        <div class="row">
                            <div class="col-md-6 mb-4">
                                <div class="form-outline">
                                    <input name="montoInput" id="noIdentificacion" 
                                           type="text" class="form-control" placeholder="${currentUser.noIdentificacion}" />
                                    <label  class="form-label" for="form3Example2">Monto (no negativo)</label>
                                </div>
                            </div>
                            <!-- identifier Input -->
                            <div class="col-md-6 mb-4">
                                <div class="form-outline">
                                    <!-- Submit button -->
                                    <button type="submit" class="btn btn-primary btn-block mb-4">
                                        Realizar transaccion
                                    </button>
                                </div>
                            </div>
                        </div>         
                    </form>

                    <c:if test="${!empty(transError)}">
                        <div class=" alert alert-danger alert-dismissible fade show" role="alert">
                            <strong>Error al tratar de hace una transaccion - </strong> ${transError}
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
            </div>
        </div>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
