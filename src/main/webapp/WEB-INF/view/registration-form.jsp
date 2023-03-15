<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="registration.form.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="nvabar.jsp" %>
<div class="container">
    <c:set var="br" value="<br>" scope="page"/>
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="registration.form.title"/>
                </h5>
                <form:form action="/registration/save" modelAttribute="customer" method="post">
                    <div class="mb-3">
                        <label for="firstName" class="form-label required-fields">
                            <fmt:message key="label.firstName"/>
                        </label>
                        <form:input path="firstName" class="form-control"/>
                        <form:errors path="firstName" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="lastName" class="form-label required-fields">
                            <fmt:message key="label.lastName"/>
                        </label>
                        <form:input path="lastName" class="form-control"/>
                        <form:errors path="lastName" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label required-fields">
                            <fmt:message key="label.email"/>
                        </label>
                        <form:input type="email" path="email" class="form-control"/>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="cell" class="form-label required-fields">
                            <fmt:message key="label.cell"/>
                        </label>
                        <form:input path="cell" class="form-control"/>
                        <form:errors path="cell" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label required-fields">
                            <fmt:message key="label.password"/>
                        </label>
                        <form:input type="password" path="password" class="form-control"/>
                        <form:errors path="password" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label required-fields">
                            <fmt:message key="label.password.confirmPassword"/>
                        </label>
                        <form:input type="password" path="confirmPassword" class="form-control"/>
                        <form:errors path="confirmPassword" cssClass="text-danger"/>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary btn-sm">
                            <fmt:message key="button.save"/>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
