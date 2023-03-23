<%--
  User: nadimmahmud
  Since: 1/25/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="order.ready.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css"/>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container">
    <c:set var="br" value="<br>" scope="page"/>
    <c:set var="ordered" value="ORDERED" scope="page"/>
    <div class="container col-md-10 mt-2">
        <div class="card">
            <%@ include file="message-view.jsp" %>
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="order.list.admin.page.title"/>
                </h5>
            </div>
            <table class="admin-order-table table align-middle text-center">
                <thead>
                <tr>
                    <th scope="col">
                        <fmt:message key="label.serial"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="label.itemList"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="label.address"/>
                    </th>
                    <th scope="col">
                        <fmt:message key="label.status"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="order" varStatus="orderStat">
                    <tr>
                        <td>
                            <c:out value="${orderStat.count}"/>
                        </td>
                        <td class="text-start">
                            <c:forEach items="${order.orderItemList}" var="orderItem">
                                <li><c:out value="${orderItem.item.name} x ${orderItem.quantity}"/></li>
                            </c:forEach>
                            </ul>
                        </td>
                        <td>
                            <c:out value="${order.address.address}"/>
                        </td>
                        <td class="${order.status == ordered ? 'bg-danger' : 'bg-success'}  p-2 text-dark bg-opacity-10">
                            <c:out value="${order.status.label}"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
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
