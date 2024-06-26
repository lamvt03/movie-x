<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/sweetalert2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/sidebarmenu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/app.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/apexcharts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/simplebar.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/dashboard.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/admin/assets/js/validateAdmin.js"></script>


<c:if test="${not empty sessionScope.loginAdmin}">
    <c:choose>
        <c:when test="${sessionScope.loginAdmin}">
            <script>
                showSwalAlert('success', 'Đăng nhập thành công');
            </script>
        </c:when>
        <c:otherwise>
            <script>
                showSwalAlert('error', 'Sai thông tin đăng nhập');
            </script>
        </c:otherwise>
    </c:choose>
    <c:remove var="loginAdmin" scope="session" />
</c:if>