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
        <fmt:message key="item.form.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="item.form.title"/>
                </h5>
                <form:form action="/admin/item/save" modelAttribute="item" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="name" class="form-label required-fields">
                            <fmt:message key="label.name"/>
                        </label>
                        <form:input path="name" class="form-control"/>
                        <form:errors path="name" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="name" class="form-label required-fields">
                            <fmt:message key="label.price"/>
                        </label>
                        <form:input path="price" class="form-control"/>
                        <form:errors path="price" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">
                            <fmt:message key="label.image"/>
                        </label>
                        <form:input type="file" path="image" class="form-control"/>
                        <form:errors path="image" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="category" class="form-label required-fields">
                            <fmt:message key="label.category"/>
                        </label>
                        <form:select path="category" class="form-select">
                            <form:options items="${categoryList}" itemLabel="name" itemValue="id"/>
                        </form:select>
                    </div>
                    <div class="mb-3">
                        <label for="tagList" class="form-label">
                            <fmt:message key="label.tag.list.colon"/>
                        </label>
                        <form:checkboxes path="tagList" items="${allTagList}" itemLabel="name"
                                         itemValue="id" class="form-check-input ms-2 me-2"/>
                    </div>
                    <div class="mb-3">
                        <label for="availability" class="form-label required-fields">
                            <fmt:message key="label.availability.colon"/>
                        </label>
                        <form:radiobuttons path="availability" items="${availabilityList}" itemLabel="label"
                                           class="form-check-input me-2 ms-2"/>
                        <form:errors path="availability" cssClass="text-danger"/>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label required-fields">
                            <fmt:message key="label.description"/>
                        </label>
                        <form:textarea path="description" class="form-control"/>
                        <form:errors path="description" cssClass="text-danger"/>
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
