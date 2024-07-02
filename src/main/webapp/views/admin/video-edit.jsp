<jsp:useBean id="video" scope="request" type="com.filmweb.dto.VideoDto"/>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/30/2023
  Time: 7:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp"%>

<html>
<head lang="en">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator - Chỉnh Sửa Video</title>

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
                    <h5 class="card-title fw-semibold mb-4 mt-2">Chỉnh Sửa Phim</h5>
                    <div class="card">
                        <div class="card-body">
                            <form id="ConfirmEditForm" action="${initParam.mvcPath}/admin/video/edit"
                                  onsubmit="return editVideo()" method="post">

                                <div class="mb-3">
                                    <label for="title" class="form-label">Tên phim</label> <input
                                        type="text" class="form-control" value="${video.title}"
                                        name="title" id="title">
                                </div>
                                <fieldset>
                                    <div class="mb-3">
                                        <label class="form-label">Link phim</label> <input
                                            type="text" class="form-control" value="${video.href}"
                                            name="href" readonly>
                                    </div>
                                </fieldset>
                                <div class="mb-3">
                                    <label for="dao-dien" class="form-label">Đạo diễn</label> <input
                                        type="text" class="form-control" value="${video.director}"
                                        name="director" id="dao-dien">
                                </div>
                                <div class="mb-3">
                                    <label for="dien-vien" class="form-label">Diễn viên</label> <input
                                        type="text" class="form-control" value="${video.actor}"
                                        name="actor" id="dien-vien">
                                </div>
                                <div class="mb-3">
                                    <label for="category" class="form-label">Thể loại</label>
                                    <select id="category" class="form-select" name="category">
                                    <option value="${video.categoryCode}" selected hidden>
                                        ${video.category}
                                    </option>

                                    <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.code}">${category.name}</option>
                                    </c:forEach>
                                </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Giá</label> <input type="text"
                                                                                 class="form-control" value="${formattedPrice}"
                                                                                 oninput="formatPrice(this)" name="price" placeholder="Giá...">
                                </div>
                                <div class="mb-3">
                                    <label for="noted" class="form-label">Mô tả</label>
                                    <textarea class="form-control" id="noted" name="description"
                                              rows="5">${video.description}</textarea>
                                </div>

                                <input type="hidden" id="confirmEdit" value="false" />

                                <button type="submit" class="btn btn-success">Xác nhận</button>
                                <button type="reset" class="btn btn-primary">Làm mới</button>
                                <a class="btn btn-danger float-end" href="${initParam.mvcPath}/admin/videos"
                                   role="button">Trở về</a>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/admin/common/footer.jsp"%>

<%
    Boolean updateVideoSuccess = (Boolean) session.getAttribute("updateVideoSuccess");

    if (updateVideoSuccess != null) {
        if (!updateVideoSuccess) {
%>
<script>
    showSwalAlert('success', 'Chỉnh sửa video thất bại');
</script>
<%
        }
        session.removeAttribute("updateVideoSuccess");
    }
%>
</body>
</html>
