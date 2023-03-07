<%--
User: nadimmahmud
Date: 12/7/22
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top bg-light px-4 mb-1 shadow-sm">
    <c:set var="adminCnst" value="ADMIN" scope="page"/>
    <c:set var="shopkeeperCnst" value="SHOPKEEPER" scope="page"/>
    <c:set var="deliveryManCnst" value="DELIVERYMAN" scope="page"/>
    <c:set var="navUser" value="user" scope="page"/>
    <c:set var="navItemList" value="item" scope="page"/>
    <c:set var="navCategory" value="category" scope="page"/>
    <c:set var="navTag" value="tag" scope="page"/>
    <c:set var="navNotification" value="notification" scope="page"/>

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
                </c:if>
                <c:if test="${activeUser.type == shopkeeperCnst}">
                <li class="nav-item">
                    <a class="nav-link active underline-hover" aria-current="page" href="/shopkeeper/notification">
                            ${navItem ==  navNotification ? '<b>Notification</b>' : 'Notification'}
                    </a>
                    </c:if>
                    <c:if test="${activeUser.type == deliveryManCnst}">
                    <a class="nav-link active underline-hover" aria-current="page" href="/delivery-man/notification">
                            ${navItem ==  navNotification? '<b>Notification</b>' : 'Notification'}
                    </a>
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