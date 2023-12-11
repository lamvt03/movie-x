<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/js/sidebarmenu.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/js/app.min.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/libs/apexcharts/dist/apexcharts.min.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/libs/simplebar/dist/simplebar.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/js/dashboard.js"></script>
<script src="${pageContext.request.contextPath}/views/admin/assets/js/validateAdmin.js"></script>

<%
    Boolean loginAdmin = (Boolean) session.getAttribute("loginAdmin");
    if (loginAdmin != null) {
        if (loginAdmin) {
%>
<script>
    showSwalAlert('success', 'Đăng nhập thành công !');
</script>
<%
} else {
%>
<script>
    showSwalAlert('error', 'Sai thông tin đăng nhập !');
</script>
<%
        }
        session.removeAttribute("loginAdmin");
    }
%>
<%
    Boolean loginAdminFail = (Boolean) session.getAttribute("loginAdminFail");
    if (loginAdminFail != null) {
        if (loginAdminFail) {
%>
<script>
    showSwalAlert('warning', 'Tài khoản không hoạt động !');
</script>
<%
        }
        session.removeAttribute("loginAdminFail");
    }
%>