<%-- 
    Document   : login
    Created on : Aug 28, 2023, 9:33:51 AM
    Author     : yenni
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <section class="mb-10 vh-100" style="background-color: #00071f;">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col col-xl-10">
                        <div class="card" style="border-radius: 1rem;">
                            <div class="row g-0">
                                <div class="col-md-6 col-lg-5 d-none d-md-block">
                                    <img src= "resources/img/login.jpg"
                                         alt="login form" class="img-fluid" style="border-radius: 1rem 0 0 1rem;" />
                                </div>
                                <div class="col-md-6 col-lg-7 d-flex align-items-center">
                                    <div class="card-body p-4 p-lg-5 text-black">

                                        <form action="${pageContext.request.contextPath}/Login" method="POST">

                                            <div class="d-flex align-items-center mb-3 pb-1">
                                                <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219;"></i>
                                                <span class="h1 fw-bold mb-0">Where's the money?</span>
                                            </div>

                                           

                                            <div class="form-outline mb-4">
                                                <input name="credentials" type="text" id="credentials" class="form-control form-control-lg" />
                                                <label class="form-label" for="form2Example17">No de dpi</label>
                                            </div>

                                            <div class="form-outline mb-4">
                                                <input name="password" type="password" id="password" class="form-control form-control-lg" />
                                                <label class="form-label" for="form2Example27">Contraseña</label>
                                            </div>
                                            
                                            <%-- en caso de que surjan errores se agregan un mensaje en el html --%>
                                            <c:if test="${!empty(error)}">
                                                <div class=" alert alert-danger alert-dismissible fade show" role="alert">
                                                    <strong>No se ha podido acceder - </strong> ${error}
                                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                                </div>
                                            </c:if>
                                            
                                            
                                            <div class="pt-1 mb-4">
                                                <button class="btn btn-dark btn-lg btn-block" type="submit">Login</button>
                                            </div>
                           
                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
