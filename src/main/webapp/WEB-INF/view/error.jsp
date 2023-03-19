<%--
  User: nadimmahmud
  Date: 12/9/22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="error.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container p-3 text-center">
    <img src="${pageContext.request.contextPath}/assets/images/exclamation-triangle-fill.svg" class="img-fluid">
    <c:choose>
        <c:when test="${errorCode == 404}">
            <h1 class="display-3">
                <fmt:message key="error.404"/>
            </h1>
        </c:when>
        <c:otherwise>
            <h1 class="display-3">
                <fmt:message key="error.unknown"/>
            </h1>
        </c:otherwise>
    </c:choose>
    <a class="btn btn-outline-primary" href="/">
        <fmt:message key="button.go.home"/>
    </a>
    <br><br>
</div>
</body>
</html>
