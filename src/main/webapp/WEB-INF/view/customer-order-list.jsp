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
        <fmt:message key="order.list.page.title"/>
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
    <c:set var="ordered" value="ORDERED" scope="page"/>
    <c:set var="delivered" value="DELIVERED" scope="page"/>
    <div class="container col-md-10 mt-2">
        <div class="card">
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
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="order.list.title"/>
                </h5>
            </div>
            <table class="table align-middle text-center">
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
                    <th scope="col">
                        <fmt:message key="label.action"/>
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
                        <td>
                            <c:if test="${order.status == ordered}">
                                <div class="d-flex justify-content-center my-1">
                                    <c:url var="updateUrl" value="/customer/order">
                                        <c:param name="orderId" value="${order.id}"/>
                                    </c:url>
                                    <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
                                        <button class="btn btn-outline-primary center btn-sm">
                                            <fmt:message key="button.update"/>
                                        </button>
                                    </a>
                                    <c:url var="cancelUrl" value="/customer/order/cancel">
                                        <c:param name="orderId" value="${order.id}"/>
                                    </c:url>
                                    <form:form class="text-center my-0 mx-2 p-0" action="${cancelUrl}" method="post">
                                        <button class="btn btn-outline-danger center btn-sm"
                                                onclick="return confirm('Want to cancel this order?')">
                                            <fmt:message key="button.cancel"/>
                                        </button>
                                    </form:form>
                                </div>
                            </c:if>
                            <c:if test="${order.status == delivered}">
                                <c:url var="cancelUrl" value="/customer/order/cancel">
                                    <c:param name="orderId" value="${order.id}"/>
                                </c:url>
                                <form:form class="text-center my-0 mx-2 p-0" action="${cancelUrl}" method="post">
                                    <button class="btn btn-outline-danger center btn-sm"
                                            onclick="return confirm('Want to delete this order?')">
                                        <fmt:message key="button.delete"/>
                                    </button>
                                </form:form>
                            </c:if>
                            <c:if test="${order.status != ordered && order.status != delivered}">
                                <fmt:message key="label.empty"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>
