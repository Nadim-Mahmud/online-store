<%--
  User: nadimmahmud
  Since: 3/21/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="dec" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
<head>
    <title><dec:title default="Online Store"/></title>
    <dec:head/>
</head>
<body class="d-flex flex-column min-vh-100">
<div class="wrapper flex-grow-1">
    <dec:body/>
</div>
<footer class="bg-light py-3 mt-4">
    <p class="text-center">© 2023 Online Store, Inc</p>
</footer>
</body>
</html>
