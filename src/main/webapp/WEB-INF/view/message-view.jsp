<%--
  User: nadimmahmud
  Since: 3/23/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
