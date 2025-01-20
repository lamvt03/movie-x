<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 20/1/25
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%--Toast notification --%>
<c:if test="${not empty sessionScope.toastMessage}">
    <script>
        showTopEndAlert('${sessionScope.toastIcon}', '${sessionScope.toastMessage}');
    </script>
    <c:remove var="toastIcon" scope="session"/>
    <c:remove var="toastMessage" scope="session"/>
</c:if>

<%--Dialog notification--%>
<c:if test="${not empty sessionScope.dialogMessage}">
    <script>
        showCenterAlert('${sessionScope.dialogIcon}', '${sessionScope.dialogTitle}' ,'${sessionScope.dialogMessage}');
    </script>
    <c:remove var="dialogIcon" scope="session"/>
    <c:remove var="dialogTitle" scope="session"/>
    <c:remove var="dialogMessage" scope="session"/>
</c:if>
