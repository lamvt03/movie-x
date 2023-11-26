<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07-Oct-23
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>
<html>
<head>
    <title>Trang chủ</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<h1>jakarata mvc home</h1>

<%@ include file="/views/common/footer.jsp" %>

<%    Boolean registerSuccess = (Boolean) session.getAttribute("registerSuccess");
    if (registerSuccess != null) {
        if (registerSuccess) {
%>
<script>
    showCenterAlert('success', 'Thành công !',
        'Một email xác minh đã gửi đến địa chỉ email của bạn !');
</script>

<%       }
    session.removeAttribute("registerSuccess");
}
%>

</body>
</html>
