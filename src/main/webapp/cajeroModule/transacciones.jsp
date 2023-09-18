<%-- 
    Document   : transacciones
    Created on : Sep 18, 2023, 12:26:25 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <c:if test="${empty(error)}"> <!-- para que no muestre nada si no tiene permisos -->

            <div class="card-body py-5 px-md-5">
                
                <div class="row d-flex justify-content-center">
                    <div class="col-lg-8">
                        <h2 class="fw-bold my-5">Transaccion: ${title}</h2>
                        <form action="${url}" method="POST">

                            <!-- 2 column grid layout with text inputs -->
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <input name="codCuentaInput" id="nameinput" type="text" class="form-control" placeholder="${currentUser.name}"/>
                                        <label class="form-label" for="form3Example1" >Codigo de cuenta a la cual realizar la transaccion</label>
                                    </div>
                                </div>
                                <!-- identifier Input -->
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <input name="montoInput" id="noIdentificacion" 
                                               type="text" class="form-control" placeholder="${currentUser.noIdentificacion}" />
                                        <label  class="form-label" for="form3Example2">Monto (no negativo)</label>
                                    </div>
                                </div>
                            </div>
                            <!-- Submit button -->
                            <button type="submit" class="btn btn-primary btn-block mb-4">
                                Realizar transaccion
                            </button>
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

        </c:if>
        <jsp:include page="/includes/footer.jsp"/>

    </body>
</html>
