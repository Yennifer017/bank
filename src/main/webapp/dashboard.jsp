<%-- 
    Document   : dashboard
    Created on : Aug 29, 2023, 7:47:42 PM
    Author     : yenni
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body style="background: #002051">
        <jsp:include page="/includes/header.jsp"/>
        <div class="list-group rounded-0" style="padding: 5% 22% 2% 22%;">
            <c:forEach items="${user.functions}" var="function">
                <a type="submit" 
                   class="mb-4 p-3 list-group-item list-group-item-action"
                   href="${function.url}"
                   >${function.title}</a>
            </c:forEach>

            <!<!-- display error -->>
            <c:if test="${!empty(error)}">
                <div class=" alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Modulo no disponible - </strong> ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
        </div>
        <jsp:include page="/includes/footer.jsp"/>
    </body>
</html>
