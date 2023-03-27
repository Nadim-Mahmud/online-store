<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="item.list.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css"/>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-end mt-3 mb-1">
        <form:form class="m-0" action="${pageContext.request.contextPath}/admin/item/form" method="get">
            <button class="btn btn-success btn-sm">
                <fmt:message key="button.add.item"/>
            </button>
        </form:form>
    </div>
    <%@ include file="../message-view.jsp" %>
    <table id="item-table" class="table table-hover table-sm align-middle text-center">
        <thead class="table-head bg-primary bg-opacity-50">
        <tr>
            <th scope="col" class="text-center">
                <fmt:message key="label.serial"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.item"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.price"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.category"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.tag.list"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.availability"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.description"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.action"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${itemList}" var="item" varStatus="itemStat">
            <tr>
                <td class="text-center">
                    <c:out value="${itemStat.count}"/>
                </td>
                <td class="text-center">
                    <c:out value="${item.name}"/>
                </td>
                <td class="text-center">
                    <c:out value="${item.price}"/>
                </td>
                <td class="text-center">
                    <c:out value="${item.category.name}"/>
                </td>
                <td class="text-center">
                    <c:if test="${item.tagList.size() == 0}">
                        <fmt:message key="label.empty"/>
                    </c:if>
                    <ul>
                        <c:forEach items="${item.tagList}" var="tag">
                            <li><c:out value="${tag.name}"/></li>
                        </c:forEach>
                    </ul>
                </td>
                <td class="text-center">
                    <c:out value="${item.availability.label}"/>
                </td>
                <td class="text-center">
                    <c:out value="${item.description}"/>
                </td>
                <td>
                    <div class="d-flex justify-content-center my-1">
                        <c:url var="updateUrl" value="${pageContext.request.contextPath}/admin/item/form">
                            <c:param name="itemId" value="${item.id}"/>
                        </c:url>
                        <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
                            <button class="btn btn-outline-primary center btn-sm">
                                <fmt:message key="button.update"/>
                            </button>
                        </a>
                        <c:url var="deleteUrl" value="${pageContext.request.contextPath}/admin/item/delete">
                            <c:param name="itemId" value="${item.id}"/>
                        </c:url>
                        <form:form class="text-center my-0 mx-2 p-0" action="${deleteUrl}" method="post">
                            <button class="btn btn-outline-danger center btn-sm"
                                    onclick="return confirm('Are you sure to delete?')">
                                <fmt:message key="button.delete"/>
                            </button>
                        </form:form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script type="text/javascript"
        src="https://code.jquery.com/jquery-3.5.1.js">
</script>
<script type="text/javascript" src=
        "https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js">
</script>
<script src="${pageContext.request.contextPath}/assets/js/pagination.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>

