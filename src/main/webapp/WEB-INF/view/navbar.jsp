<%--
User: nadimmahmud
Date: 12/7/22
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.onlinestore" var="lang"/>
<nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top bg-light px-4 mb-1 shadow-sm">
    <c:set var="adminCnst" value="ADMIN" scope="page"/>
    <c:set var="deliveryManCnst" value="DELIVERYMAN" scope="page"/>
    <c:set var="customerCnst" value="CUSTOMER" scope="page"/>
    <c:set var="orderDelivered" value="DELIVERED" scope="page"/>
    <c:set var="navUser" value="user" scope="page"/>
    <c:set var="navOrderForm" value="orderForm" scope="page"/>
    <c:set var="navOrderList" value="orderList" scope="page"/>
    <c:set var="navOrderHistory" value="orderHistory" scope="page"/>
    <c:set var="navItemList" value="item" scope="page"/>
    <c:set var="navCategory" value="category" scope="page"/>
    <c:set var="navTag" value="tag" scope="page"/>
    <c:set var="navAllOrder" value="allOrder" scope="page"/>
    <c:set var="navNotification" value="notification" scope="page"/>
    <c:set var="navReadyOrder" value="readyOrder" scope="page"/>
    <c:set var="navDeliveryList" value="deliveryList" scope="page"/>
    <c:set var="navDeliveryHistory" value="deliveryHistory" scope="page"/>

    <div class="container-fluid m-0">
        <a href="/" class="navbar-brand">
            <fmt:message key="app.title"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${activeUser.type == adminCnst}">
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/shopkeeper/list">
                                ${navItem == navUser ? '<b>User</b>' : 'User'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/category">
                                ${navItem == navCategory ? '<b>Category</b>' : 'Category'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/item">
                                ${navItem == navItemList ? '<b>Item</b>' : 'Item'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/tag">
                                ${navItem == navTag ? '<b>Tag</b>' : 'Tag'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/all-order">
                                ${navItem == navAllOrder ? '<b>All Order</b>' : 'All Order'}
                        </a>
                    </li>
                </c:if>
                <c:if test="${activeUser.type == deliveryManCnst}">
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/">
                                ${navItem ==  navReadyOrder? '<b>Ready orders</b>' : 'Ready orders'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/delivery/delivery-list">
                                ${navItem ==  navDeliveryList? '<b>Delivery list</b>' : 'Delivery list'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/delivery/delivery-history">
                                ${navItem ==  navDeliveryHistory? '<b>Delivery history</b>' : 'Delivery history'}
                        </a>
                    </li>
                </c:if>
                <c:if test="${activeUser.type == customerCnst}">
                    <li>
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/customer/order">
                                ${navItem ==  navOrderForm? '<b>New order</b>' : 'New order'}
                        </a>
                    </li>
                    <li>
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/customer/order-list">
                                ${navItem ==  navOrderList? '<b>Orders</b>' : 'Orders'}
                        </a>
                    </li>
                    <li>
                        <a class="nav-link active underline-hover" aria-current="page"
                           href="/customer/order-history">
                                ${navItem ==  navOrderHistory? '<b>Orders history</b>' : 'Orders history'}
                        </a>
                    </li>
                </c:if>
            </ul>
            <span class="navbar-text">
                <c:if test="${activeUser != null}">
                    <div class="nav-item dropdown">
                        <p class="nav-link dropdown-toggle text-black m-0 underline-hover" role="button"
                           data-bs-toggle="dropdown"
                           aria-expanded="false">${activeUser.firstName}&nbsp${activeUser.lastName}&nbsp;(${activeUser.type.label}) &nbsp;
                        </p>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item" href="/update-profile">
                                    <fmt:message key="button.update.profile"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="/update-password">
                                    <fmt:message key="button.update.password"/>
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="m-0 dropdown-item" href="/logout">
                                    <fmt:message key="button.logout"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${activeUser == null && loginPage == null}">
                    <a href="/login-page" type="button" class="btn btn-primary btn-sm px-4 text-white">
                        <fmt:message key="button.login"/>
                    </a>
                </c:if>
            </span>
        </div>
    </div>
</nav>