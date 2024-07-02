<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/1/2023
  Time: 9:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrator - Lượt Thích Theo Từng Video</title>

    <%@ include file="/views/admin/common/head.jsp" %>
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

                    <h5 class="card-title fw-semibold mb-4 mt-2">Danh Sách Các
                        Lượt Yêu Thích Từng Video</h5>
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Tên phim</th>
                                        <th scope="col">Đạo diễn</th>
                                        <th scope="col">Số lượt thích</th>
                                        <th scope="col">Trạng thái</th>
<%--                                        <th scope="col">Hành động</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <jsp:useBean id="videos" scope="request" type="java.util.List"/>
                                    <c:forEach items="${videos}" var="video" varStatus="loop">
                                        <tr>
                                            <td scope="row">${loop.index + 1}</td>
                                            <td>${video.title}</td>
                                            <td>${video.director}</td>
                                            <td width="130px">${video.likeQuantity}</td>
                                            <td width="155px"><c:choose>
                                                <c:when test="${video.isActive}">Đang công chiếu</c:when>
                                                <c:otherwise>Ngưng công chiếu</c:otherwise>
                                            </c:choose></td>
<%--                                            <td><button type="button" data-bs-toggle="modal"--%>
<%--                                                        data-bs-target="#modalLiveDemo${loop.index}"--%>
<%--                                                        class="btn btn-success ms-2 rounded-2">Xem</button></td>--%>
                                        </tr>

<%--                                        <!-- Modal -->--%>
<%--                                        <div class="modal fade" id="modalLiveDemo${loop.index}"--%>
<%--                                             tabindex="-1" aria-labelledby="exampleModalLabel"--%>
<%--                                             aria-hidden="true">--%>
<%--                                            <div class="modal-dialog modal-xl modal-dialog-centered">--%>
<%--                                                <div class="modal-content">--%>
<%--                                                    <iframe id="player" width="100%" height="600"--%>
<%--                                                            src="https://www.youtube.com/embed/${video.href}"--%>
<%--                                                            frameborder="0" allowfullscreen></iframe>--%>
<%--                                                </div>--%>
<%--                                            </div>--%>
<%--                                        </div>--%>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage == 1}">
                                <li class="page-item text-secondary disabled"><a
                                        class="page-link" href="#" aria-disabled="true"> <i
                                        class="ti ti-chevron-left"></i>
                                </a></li>
                            </c:if>

                            <c:if test="${currentPage > 1}">
                                <li class="page-item text-secondary"><a class="page-link"
                                                                        href="${initParam.mvcPath}/admin/videos/liked?page=${currentPage - 1}"
                                                                        aria-disabled="true">
                                    <i class="ti ti-chevron-left"></i>
                                </a></li>
                            </c:if>

                            <c:forEach varStatus="i" begin="1" end="${maxPage}">
                                <li
                                        class="page-item text-secondary ${currentPage == i.index ? 'active' : ''}">
                                    <a class="page-link"
                                       href="${initParam.mvcPath}/admin/videos/liked?page=${i.index}">${i.index}</a>
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
                                                                        href="${initParam.mvcPath}/admin/videos/liked?page=${currentPage + 1}"
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

<%@ include file="/views/admin/common/footer.jsp"%>
</body>
</html>
