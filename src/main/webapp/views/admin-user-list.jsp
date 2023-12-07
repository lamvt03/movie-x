<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/30/2023
  Time: 10:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator - Danh Sách Người Dùng</title>

    <%@ include file="/views/admin/common/head.jsp" %>
</head>
<body>

<!--  Body Wrapper -->
<div class="page-wrapper" id="main-wrapper" data-layout="vertical"
     data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed">

    <!-- Sidebar Start -->
    <%@ include file="/views/admin/common/assied.jsp" %>
    <!--  Sidebar End -->

    <!--  Main wrapper -->
    <div class="body-wrapper">

        <!--  Header Start -->
        <%@ include file="/views/admin/common/header.jsp" %>
        <!--  Header End -->

        <div class="container-fluid">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title fw-semibold mb-4 mt-2">Danh Sách Người Dùng</h5>
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Họ và tên</th>
                                        <th scope="col">Số điện thoại</th>
                                        <th scope="col">Vai trò</th>
                                        <th scope="col">Hành động</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <jsp:useBean id="users" scope="request" type="java.util.List"/>
                                    <c:forEach items="${users}" var="user" varStatus="loop">
                                        <tr class="${user.isActive == false ? 'bg-light' : ''}">

                                            <td scope="row">${loop.index + 1}</td>
                                            <td>${user.email}</td>
                                            <td>${user.fullName}</td>
                                            <td>${user.phone}</td>
                                            <td><c:choose>
                                                <c:when test="${user.isAdmin}">Quản trị</c:when>
                                                <c:otherwise>Người dùng</c:otherwise>
                                            </c:choose></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.isAdmin}"></c:when>
                                                    <c:otherwise>
                                                        <div class="btn-group" role="group">
                                                            <button class="btn btn-primary ms-2 rounded-2"
                                                                    onclick="GetUserId('${user.id}')">Sửa
                                                            </button>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>


                                        <form id="EditUserForm" action="edituser" method="get">
                                            <input type="hidden" id="UserId" name="id">
                                        </form>

                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>

                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage == 1}">
                                <li class="page-item text-secondary disabled">
                                    <a
                                            class="page-link" href="#" aria-disabled="true"> <i
                                            class="ti ti-chevron-left"></i>
                                    </a>
                                </li>
                            </c:if>

                            <c:if test="${currentPage > 1}">
                                <li class="page-item text-secondary"><a class="page-link"
                                                                        href="${initParam['mvcPath']}/admin/users?page=${currentPage - 1}"
                                                                        aria-disabled="true">
                                    <i class="ti ti-chevron-left"></i>
                                </a></li>
                            </c:if>

                            <c:forEach varStatus="i" begin="1" end="${maxPage}">
                                <li
                                        class="page-item text-secondary ${currentPage == i.index ? 'active' : ''}">
                                    <a class="page-link"
                                       href="${initParam['mvcPath']}/admin/users?page=${i.index}">${i.index}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage == maxPage}">
                                <li class="page-item text-secondary disabled"><a
                                        class="page-link" href="#" aria-disabled="true"> <i
                                        class="ti ti-chevron-right"></i>
                                </a></li>
                            </c:if>

                            <c:if test="${currentPage < maxPage}">
                                <li class="page-item text-secondary"><a class="page-link"
                                                                        href="${initParam['mvcPath']}/admin/users?page=${currentPage + 1}"
                                                                        aria-disabled="true">
                                    <i class="ti ti-chevron-right"></i>
                                </a></li>
                            </c:if>
                        </ul>
                    </nav>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/admin/common/footer.jsp" %>

<script type="text/javascript">
    // lấy href	sử dụng cho edit user
    function GetUserId(UserId) {
        document.getElementById("UserId").value = UserId;
        document.getElementById("EditUserForm").submit();
    }
</script>

<%
    Boolean EditMessage = (Boolean) session.getAttribute("EditMessage");
    if (EditMessage != null) {
        if (EditMessage) {
%>
<script>
    showCenterAlert('error', 'Thất bại !',
        'Không thể chỉnh sửa nhân viên quản trị !');
</script>
<%
        }
        session.removeAttribute("EditMessage");
    }
%>
</body>
</html>
