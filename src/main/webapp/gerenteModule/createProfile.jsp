<%-- 
    Document   : createProfile
    Created on : Aug 31, 2023, 3:07:59 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>create Profile</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <c:if test="${empty(error)}"> <!-- para que no muestre nada si no tiene permisos -->
            <!-- Section: Design Block -->
            <section class="text-center">
                <!-- Background image -->
                <div class="p-5 bg-image" style="
                     background-image: url('https://mdbootstrap.com/img/new/textures/full/171.jpg');
                     height: 100px;
                     "></div>
                <!-- Background image -->

                <div class="card mx-4 mx-md-5 shadow-5-strong" style="
                     margin-top: -100px;
                     background: hsla(0, 0%, 100%, 0.8);
                     backdrop-filter: blur(30px);
                     ">
                    <div class="card-body py-5 px-md-5">

                        <div class="row d-flex justify-content-center">
                            <div class="col-lg-8">
                                <h2 class="fw-bold mb-5">Creacion de usuario</h2>
                                <form action="${url}" method="POST">
                                    <!-- 2 column grid layout with text inputs for the first and last names -->
                                    <div class="row">
                                        <div class="col-md-6 mb-4">
                                            <div class="form-outline">
                                                <input name="nameinput" id="nameinput" type="text" class="form-control" />
                                                <label class="form-label" for="form3Example1">Nombre completo</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6 mb-4">
                                            <div class="form-outline">
                                                <input name="noIdentificacion" id="noIdentificacion" type="text" class="form-control" />
                                                <label  class="form-label" for="form3Example2">No de identificacion personal (DPI o pasaporte)</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <div class="form-outline">
                                            <input name="addressinput" id="addressinput" type="text" class="form-control" />
                                            <label class="form-label" for="form3Example1">Dirección de domicilio</label>
                                        </div>
                                    </div>
                                    <!-- Password input -->
                                    <div class="form-outline mb-4">
                                        <input name="password" id="password" type="password" id="form3Example4" class="form-control" />
                                        <label  class="form-label" for="form3Example4">Contrasena</label>
                                    </div>
                                    <!-- sex input -->
                                    <select name="sexInput" class="p-2 mb-4 form-select" aria-label="Default select example">
                                        <option selected>Sexo</option>
                                        <option value="1">Masculino</option>
                                        <option value="2">Femenino</option>
                                    </select>
                                    <!-- birth input -->
                                    <div class="form-outline mb-4">
                                        <input name="birthInput" type="date">
                                        <label  class="form-label" for="form3Example4">Año de nacimiento</label>
                                    </div>

                                    <!-- pdf upload -->
                                    <div class="form-outline">
                                        <input name="archivo" accept=".pdf" 
                                               type="file" class="form-control" 
                                               id="archivo" 
                                               aria-describedby="inputGroupFileAddon04" 
                                               aria-label="Upload"/>
                                        <label  class="form-label" for="form3Example2">Fotografia de DPI, formato pdf</label>
                                    </div>

                                    <!-- Submit button -->
                                    <button type="submit" class="btn btn-primary btn-block mb-4">
                                        Aceptar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Section: Design Block -->
        </c:if>>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
