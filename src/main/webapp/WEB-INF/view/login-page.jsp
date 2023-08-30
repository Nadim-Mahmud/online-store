<%--
  User: nadimmahmud
  Date: 12/5/22
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="login.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="container col-md-8 mt-5">
        <div class="card">
            <div class="card-body">
                <div class="login">
                    <div class="row justify-content-center">
                        <div class="col">
                            <img src="${pageContext.request.contextPath}/assets/images/shop.png"
                                 class="rounded mx-auto d-block">
                        </div>
                        <div class="col">
                            <div class="d-flex">
                                <div class="w-100">
                                    <h3 class="mb-4">
                                        <fmt:message key="label.sign.in"/>
                                    </h3>
                                </div>
                            </div>
                            <c:if test="${success != null}">
                                <p class="text-success">
                                    &check; ${success}!
                                </p>
                            </c:if>
                            <div class="d-flex text-danger py-2">
                                <c:if test="${invalidLogin != null}">
                                    <fmt:message key="fail.invalid.emailOrPassword"/>
                                </c:if>
                            </div>
                            <form:form action="/login" modelAttribute="credentials" method="post">
                                <div class="form-group mb-3">
                                    <label class="label">
                                        <fmt:message key="label.email"/>
                                    </label>
                                    <form:input path="email" type="email" class="form-control" placeholder="Email"
                                                required="required"/>
                                </div>
                                <div class="form-group mb-3">
                                    <label class="label">
                                        <fmt:message key="label.password"/>
                                    </label>
                                    <form:input path="password" type="password" class="form-control"
                                                placeholder="Password"
                                                required="required"/>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="form-control btn btn-primary rounded submit px-3">
                                        <fmt:message key="button.sign.in"/>
                                    </button>
                                </div>
                            </form:form>
                            <p class="text-center mt-3">
                                Do not have a account?
                                <a data-toggle="tab" href="/registration">
                                    Sign Up
                                </a>
                                here.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>
</html>
