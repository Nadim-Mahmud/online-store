<%--
  User: nadimmahmud
  Since: 1/17/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="home.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<c:set var="available" value="AVAILABLE" scope="page"/>
<c:set var="color" value="" scope="page"/>
<jsp:include page="navbar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-between mt-3">
        <div class="col-md-4">
            <form class="m-0" action="/" method="get">
                <div class="input-group">
                    <select name="categoryId" id="categoryId" class="form-control">
                        <option value="" ${categoryId == null ? 'selected' : ''}>-- Select Category --</option>
                        <c:forEach items="${categoryList}" var="category">
                            <option value="${category.id}" ${categoryId == category.id ? 'selected' : ''}>
                                <c:out value="${category.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <select name="tagId" id="tagId" class="form-control">
                        <option value="" ${tagId == null ? 'selected' : ''}>-- Select Tag --</option>
                        <c:forEach items="${tagList}" var="tag">
                            <option value="${tag.id}" ${tagId == tag.id ? 'selected' : ''}>
                                <c:out value="${tag.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                    <div class="input-group-addon input-group-button">
                        <button type="submit" class="btn btn-primary">Filter</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-4">
            <form class="d-flex m-0" action="/item/search" method="get">
                <input name="searchKey" id="searchKey" class="form-control me-2"
                       placeholder="Search: name, description"
                       aria-label="Search" value="${searchKey}">
                <button class="btn btn-primary" type="submit">
                    <fmt:message key="button.search"/>
                </button>
            </form>
        </div>
    </div>
    <div class="row mt-3 item-list">
        <c:forEach items="${itemList}" var="item">
            <div class="col-md-3 p-1">
                <div class="card shadow-sm mb-1 bg-body-tertiary rounded">
                    <c:url var="imageUrl" value="/item/image">
                        <c:param name="itemId" value="${item.id}"/>
                    </c:url>
                    <img src="${imageUrl}" class="card-img-top" width="200px" height="200px"
                         alt="image">
                    <div class="card-body">
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
                        <c:url var="itemUrl" value="/item/details">
                            <c:param name="itemId" value="${item.id}"/>
                        </c:url>
                        <a href="${itemUrl}" class="btn btn-primary">
                            <fmt:message key="button.details"/>
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:url var="nextPageUrl" value="${searchKey == null ? '/' : '/item/search'}">
            <c:param name="pageStartValue" value="${pageStartValue}"/>
            <c:param name="categoryId" value="${categoryId}"/>
            <c:param name="tagId" value="${tagId}"/>
            <c:param name="searchKey" value="${searchKey}"/>
            <c:param name="pageType" value="NEXT_PAGE"/>
        </c:url>
        <c:url var="previousPageUrl" value="${searchKey == null ? '/' : '/item/search'}">
            <c:param name="categoryId" value="${categoryId}"/>
            <c:param name="tagId" value="${tagId}"/>
            <c:param name="searchKey" value="${searchKey}"/>
            <c:param name="pageStartValue" value="${pageStartValue}"/>
            <c:param name="pageType" value="PREVIOUS_PAGE"/>
        </c:url>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="${previousPageUrl}">Previous</a></li>
                <c:if test="${!(empty itemList)}">
                    <li class="page-item"><a class="page-link" href="${nextPageUrl}">Next</a></li>
                </c:if>
            </ul>
        </nav>
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
