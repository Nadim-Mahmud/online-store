<%--
  User: nadimmahmud
  Since: 3/7/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="address.form.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="address.form.title"/>
                </h5>
                <form:form class="m-0 p - 0 " action="/customer/order/save" modelAttribute="address" method="post">
                    <div class="mb-3">
                        <label for="title" class="form-label">
                            <fmt:message key="label.title"/>
                        </label>
                        <form:input path="title" class="form-control"/>
                        <form:errors path="title" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">
                            <fmt:message key="label.address"/>
                        </label>
                        <form:input path="address" class="form-control"/>
                        <form:errors path="address" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="note" class="form-label">
                            <fmt:message key="label.note"/>
                        </label>
                        <form:textarea path="note" class="form-control"/>
                        <form:errors path="note" cssClass="text-danger"/>
                    </div>
                    <div class="d-flex justify-content-between">
                        <a class="btn btn-sm bg-secondary text-white" href="/customer/order">
                            &larr; <fmt:message key="button.back.page"/>
                        </a>
                        <button type="submit" class="btn btn-sm btn-primary">
                            <fmt:message key="button.place.order"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/ajax.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
