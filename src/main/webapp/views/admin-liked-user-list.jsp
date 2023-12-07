<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/1/2023
  Time: 11:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp"%>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrator - Người Dùng Thích Video</title>

    <%@ include file="/views/admin/common/head.jsp"%>
</head>
<body>
<!--  Body Wrapper -->
<div class="page-wrapper" id="main-wrapper" data-layout="vertical"
     data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed">

    <!-- Sidebar Start -->
    <%@ include file="/views/admin/common/assied.jsp"%>
    <!--  Sidebar End -->

    <!--  Main wrapper -->
    <div class="body-wrapper">

        <!--  Header Start -->
        <%@ include file="/views/admin/common/header.jsp"%>
        <!--  Header End -->

        <div class="container-fluid">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title fw-semibold mb-4 mt-2">Danh Sách Người
                        Dùng Yêu Thích Video</h5>
                    <div class="card">
                        <div class="card-body">
                            <form action="userlike" method="get">
                                <div class="mb-3">
                                    <label class="form-label">Tên phim:</label> <select
                                        name="href" id="selectVideo"
                                        class="form-select form-select-lg rounded-2 fs-3"
                                        style="height: 38.6px;">
                                    <option selected disabled>---Chọn tên phim---</option>
                                    <jsp:useBean id="videos" scope="request" type="java.util.List"/>
                                    <c:forEach items="${videos}" var="video">
<%--                                        <jsp:useBean id="href" scope="request" type="java.lang.String"/>--%>

                                        <option value="${video.href}"
                                            ${href == video.href ? 'selected' : ''}>${video.title}</option>
                                    </c:forEach>
                                </select>
                                </div>

                                <button type="submit" class="btn btn-success">Tìm kiếm</button>
                            </form>
                        </div>
                    </div>

                    <h5 class="card-title fw-semibold mb-4 mt-2">Danh Sách</h5>
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Họ tên</th>
                                        <th scope="col">Số điện thoại</th>
                                        <th scope="col">Trạng thái</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <jsp:useBean id="users" scope="request" type="java.util.List"/>
                                    <c:forEach items="${users}" var="user" varStatus="loop">
                                        <tr>
                                            <td scope="row">${loop.index + 1}</td>
                                            <td>${user.email}</td>
                                            <td>${user.fullName}</td>
                                            <td>${user.phone}</td>
                                            <td><c:choose>
                                                <c:when test="${user.isActive}">Đang hoạt động</c:when>
                                                <c:otherwise>Ngưng hoạt động</c:otherwise>
                                            </c:choose></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/admin/common/footer.jsp"%>
</body>
</html>
