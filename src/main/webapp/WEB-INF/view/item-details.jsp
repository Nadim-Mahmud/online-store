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
        <fmt:message key="item.details.page.title"/>
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
                <div class="text-center">
                    <c:if test="${success != null}">
                        <p class="text-success">
                            &check; ${success}!
                        </p>
                    </c:if>
                    <c:if test="${failed != null}">
                        <p class="text-danger">
                            &cross; ${failed}
                        </p>
                    </c:if>
                </div>
                <div class="card shadow-sm mb-1 bg-body-tertiary rounded">
                    <img src="/item/image?itemId=${item.id}" class="card-img-top" alt="image">
                    <div class="card-body">
                        <c:if test="${activeUser != null && activeUser.type == customerCnst}">
                            <div class="m-2 mb-1">
                                <c:url var="addItemUrl" value="/customer/add-item">
                                    <c:param name="detailsPage" value="${true}"/>
                                    <c:param name="itemId" value="${item.id}"/>
                                </c:url>
                                <form:form class="d-flex m-2" modelAttribute="orderItem" action="${addItemUrl}"
                                           method="post">
                                    <form:input path="item" type="hidden" value="${item.id}"/>
                                    <form:input class="form-control me-2" placeholder="Quantity" aria-label="Quantity"
                                                path="quantity"/>
                                    <form:errors path="quantity" cssClass="text-danger"/>
                                    <button class="btn btn-success btn-sm text-nowrap" type="submit">
                                        <fmt:message key="button.add.to.cart"/>
                                    </button>
                                </form:form>
                            </div>
                        </c:if>
                        <h5 class="card-title">${item.name}</h5>
                        <hr>
                        <p class="m-0">
                            <fmt:message key="label.category.colon"/>
                            ${item.category.name}
                        </p>
                        <p class="m-0">
                            <fmt:message key="label.price.colon"/>
                            ${item.price}
                            <fmt:message key="label.taka"/>
                        </p>
                        <p class="m-0 ${item.availability != available ? 'text-danger' : ''}">
                            <fmt:message key="label.availability.colon"/> ${item.availability.label}
                        </p>
                        <p class="m-0 mb-2">
                            <fmt:message key="label.tag.colon"/>
                            <c:forEach items="${item.tagList}" var="tag">
                                <c:out value="${tag.name}, "/>
                            </c:forEach>
                        </p>
                        <hr>
                        <p class="card-text">${item.description}</p>
                    </div>
                </div>
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
