<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/30/2023
  Time: 10:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator - Danh Sách Top Người Dùng</title>

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
                                        <th scope="col">Tổng tiền</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <jsp:useBean id="topUsers" scope="request" type="java.util.List"/>
                                    <c:forEach items="${topUsers}" var="topUser" varStatus="loop">
                                        <tr class="${topUser.isActive == false ? 'bg-light' : ''}">

                                            <td scope="row">${loop.index + 1}</td>
                                            <td>${topUser.email}</td>
                                            <td>${topUser.fullName}</td>
                                            <td>${topUser.phone}</td>
                                            <td>${topUser.formattedTotalBalanceAmount}</td>
                                        </tr>

                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>

                        </div>
                    </div>

<%--                    <nav aria-label="Page navigation example">--%>
<%--                        <ul class="pagination justify-content-center">--%>
<%--                            <c:if test="${currentPage == 1}">--%>
<%--                                <li class="page-item text-secondary disabled">--%>
<%--                                    <a--%>
<%--                                            class="page-link" href="#" aria-disabled="true"> <i class="fa-solid fa-caret-left"></i>--%>
<%--                                    </a>--%>
<%--                                </li>--%>
<%--                            </c:if>--%>

<%--                            <c:if test="${currentPage > 1}">--%>
<%--                                <li class="page-item text-secondary"><a class="page-link"--%>
<%--                                                                        href="${initParam.mvcPath}/admin/users?page=${currentPage - 1}"--%>
<%--                                                                        aria-disabled="true">--%>
<%--                                    <i class="fa-solid fa-caret-left"></i>--%>
<%--                                </a></li>--%>
<%--                            </c:if>--%>

<%--                            <c:forEach varStatus="i" begin="1" end="${maxPage}">--%>
<%--                                <li--%>
<%--                                        class="page-item text-secondary ${currentPage == i.index ? 'active' : ''}">--%>
<%--                                    <a class="page-link"--%>
<%--                                       href="${initParam.mvcPath}/admin/users?page=${i.index}">${i.index}</a>--%>
<%--                                </li>--%>
<%--                            </c:forEach>--%>

<%--                            <c:if test="${currentPage == maxPage}">--%>
<%--                                <li class="page-item text-secondary disabled"><a--%>
<%--                                        class="page-link" href="#" aria-disabled="true"> <i class="fa-solid fa-caret-right"></i>--%>
<%--                                </a></li>--%>
<%--                            </c:if>--%>

<%--                            <c:if test="${currentPage < maxPage}">--%>
<%--                                <li class="page-item text-secondary"><a class="page-link"--%>
<%--                                                                        href="${initParam.mvcPath}/admin/users?page=${currentPage + 1}"--%>
<%--                                                                        aria-disabled="true">--%>
<%--                                    <i class="fa-solid fa-caret-right"></i>--%>
<%--                                </a></li>--%>
<%--                            </c:if>--%>
<%--                        </ul>--%>
<%--                    </nav>--%>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/admin/common/footer.jsp" %>

</body>
</html>
