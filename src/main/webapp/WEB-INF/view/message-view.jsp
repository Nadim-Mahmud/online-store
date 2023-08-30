<%--
  User: nadimmahmud
  Since: 3/23/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="text-center">
  <c:if test="${success != null}">
    <div class="alert alert-success m-1 p-1" role="alert">
      &check; ${success}!
    </div>
  </c:if>
  <c:if test="${failed != null}">
    <div class="alert alert-danger m-1 p-1" role="alert">
      &cross; ${failed}
    </div>
  </c:if>
</div>
