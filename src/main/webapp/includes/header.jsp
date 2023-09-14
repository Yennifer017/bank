<%-- 
    Document   : header
    Created on : Aug 31, 2023, 8:44:17 PM
    Author     : yenni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<nav class="navbar navbar-expand-lg navbar-light bg-dark fixed-top">
    <!-- Container wrapper -->
    <div class="container">

        <!-- Toggle button -->
        <button
            class="navbar-toggler"
            type="button"
            data-mdb-toggle="collapse"
            data-mdb-target="#navbarButtonsExample"
            aria-controls="navbarButtonsExample"
            aria-expanded="false"
            aria-label="Toggle navigation"
            >
            <i class="fas fa-bars"></i>
        </button>

        <!-- Collapsible wrapper -->
        <div class="collapse navbar-collapse" id="navbarButtonsExample">
            <!-- Left links -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link text-white" href="dashboard.jsp">Bienvenido(a) ${user.name}</a>
                </li>
            </ul>
            <!-- Left links -->

            <div class="d-flex align-items-center">
                <a class="btn btn-primary me-3" href="${pageContext.request.contextPath}/login"> Cerrar Sesion</a>
            </div>
        </div>
        <!-- Collapsible wrapper -->
    </div>
    <!-- Container wrapper -->
</nav>
<!-- Navbar -->
