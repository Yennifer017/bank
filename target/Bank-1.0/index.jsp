<%-- 
    Document   : index
    Created on : Sep 13, 2023, 8:14:19 PM
    Author     : yenni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bank: Where's the money?</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <header>
            <!-- Intro settings -->
            <style>
              /* Default height for small devices */
              #intro {
                height: 500px;
              }

              /* Height for devices larger than 900px */
              @media (min-width: 900px) {
                #intro {
                  height: 680px;
                }
              }
            </style>

            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg navbar-light bg-white fixed-top">
                <div class="container-fluid">
                    <button
                        class="navbar-toggler"
                        type="button"
                        data-mdb-toggle="collapse"
                        data-mdb-target="#navbarExample01"
                        aria-controls="navbarExample01"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                        >
                        <i class="fas fa-bars"></i>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarExample01">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item active">
                                <a class="nav-link" aria-current="page" href="#">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Features</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">Pricing</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">About</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- Navbar -->

            <!-- Background image -->
            <div
                id="intro"
                class=" text-center bg-image "
                style="background-image: url('resources/img/wallpaper.jpg');
                padding: 150px;"
                >   
                <div class="mask" style="background-color: rgba(0, 0, 0, 0.7);">
                    <div class="d-flex justify-content-center align-items-center h-100">
                        <div class="text-white">
                            <h1 class="mb-3">WHERE'S THE MONEY</h1>
                            <h5 class="mb-4">Your bank</h5>
                            <a
                                class="btn btn-outline-light btn-lg m-2"
                                href="${pageContext.request.contextPath}/login.jsp"
                                role="button"
                                rel="nofollow"
                                target="_self"
                                >Ingresar</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Background image -->
        </header>

        <!-- cards -->
        <div class="container-auto m-5 row row-cols-1 row-cols-md-3 g-4">
            <div class="col">
                <div class="card h-100">
                    <img src="resources/img/card1.jpg" class="card-img-top" alt="Hollywood Sign on The Hill"/>
                    <div class="card-body">
                        <h5 class="card-title">Incrementa tus ahorros</h5>
                        <p class="card-text">
                            Tenemos altas tasas de interes para ahorros a plazo fijo
                            para que tu dinero pueda rendir mas. Pregunta por ellos.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card h-100">
                    <img src="resources/img/card3.jpg" class="card-img-top" alt="Palm Springs Road"/>
                    <div class="card-body">
                        <h5 class="card-title">Cuida tu dinero con nosotros</h5>
                        <p class="card-text">Tu dinero esta a salvo y disponible cuando lo necesites
                            Somos el mejor banco    .</p>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card h-100">
                    <img src="resources/img/card2.jpg" class="card-img-top" alt="Los Angeles Skyscrapers"/>
                    <div class="card-body">
                        <h5 class="card-title">Un servicio automatizado</h5>
                        <p class="card-text">Con un portal web bastante accecible y tus servicios
                            super rapidos, al igual que tus datos siempre estaran protegidos.</p>
                    </div>
                </div>
            </div>
            
        </div>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
