<%--
  User: nadimmahmud
  Since: 3/6/23
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
        <fmt:message key="user.list.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css"/>
</head>
<body>
<c:set var="shopkeeper" value="shopkeeper" scope="page"/>
<c:set var="deliveryMan" value="deliveryMan" scope="page"/>
<c:set var="customer" value="customer" scope="page"/>
<jsp:include page="../navbar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-end mt-3 mb-1">
        <form:form class="m-0" action="${pageContext.request.contextPath}/admin/user/form" method="get">
            <button class="btn btn-success btn-sm">
                <fmt:message key="button.add.user"/>
            </button>
        </form:form>
    </div>
    <div class="d-flex justify-content-start mt-3 mb-1">
        <a class="m-0" href="${pageContext.request.contextPath}/admin/shopkeeper/list">
            <button class="btn ${userType == shopkeeper ? 'btn-primary' : 'btn-outline-primary'} btn-sm">
                <fmt:message key="shopkeeper"/>
            </button>
        </a>
        <a class="m-0" href="${pageContext.request.contextPath}/admin/deliveryMan/list">
            <button class="btn ${userType == deliveryMan ? 'btn-primary' : 'btn-outline-primary'} btn-sm mx-2">
                <fmt:message key="deliveryman"/>
            </button>
        </a>
        <a class="m-0" href="${pageContext.request.contextPath}/admin/customer/list">
            <button class="btn ${userType == customer ? 'btn-primary' : 'btn-outline-primary'} btn-sm">
                <fmt:message key="customer"/>
            </button>
        </a>
    </div>
    <%@ include file="../message-view.jsp"%>
    <table id="user-table" class="table table-hover table-sm align-middle text-center">
        <thead class="table-head bg-primary bg-opacity-50">
        <tr>
            <th scope="col" class="text-center">
                <fmt:message key="label.serial"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.firstName"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.lastName"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.email"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.cell"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.action"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList}" var="user" varStatus="shopkeeperStat">
            <tr>
                <td class="text-center">
                    <c:out value="${shopkeeperStat.count}"/>
                </td>
                <td class="text-center">
                    <c:out value="${user.firstName}"/>
                </td>
                <td class="text-center">
                    <c:out value="${user.lastName}"/>
                </td>
                <td class="text-center">
                    <c:out value="${user.email}"/>
                </td>
                <td class="text-center">
                    <c:out value="${user.cell}"/>
                </td>
                <td>
                    <div class="d-flex justify-content-center my-1">
                        <c:url var="updateUrl" value="${pageContext.request.contextPath}/admin/user/form">
                            <c:param name="userId" value="${user.id}"/>
                        </c:url>
                        <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
                            <button class="btn btn-outline-primary center btn-sm">
                                <fmt:message key="button.update"/>
                            </button>
                        </a>
                        <c:url var="deleteUrl" value="${pageContext.request.contextPath}/admin/user/delete">
                            <c:param name="userId" value="${user.id}"/>
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
<script src="${pageContext.request.contextPath}/assets/js/ajax.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>