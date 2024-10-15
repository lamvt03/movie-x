<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/30/2023
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator - Thêm phim mới</title>

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
                    <h5 class="card-title fw-semibold mb-4 mt-2">Thêm Mới Phim</h5>
                    <div class="card">
                        <div class="card-body">
                            <form onsubmit="return validateNewFilm()" action="${initParam.mvcPath}/admin/video/add"
                                  method="post">

                                <div class="mb-3">
                                    <label for="title" class="form-label">Tên phim</label> <input
                                        type="text" class="form-control" name="title" id="title"
                                        placeholder="Nhập tên phim">
                                </div>
                                <div class="mb-3">
                                    <label for="link-phim" class="form-label">Href phim</label> <input
                                        type="text" class="form-control" name="href" id="link-phim"
                                        placeholder="Nhập href phim" onblur="fillHrefOnPoster()">
                                </div>
                                <div class="mb-3">
                                    <label for="dao-dien" class="form-label">Đạo diễn</label> <input
                                        type="text" class="form-control" name="director" id="dao-dien"
                                        placeholder="Nhập tên đạo diễn">
                                </div>
                                <div class="mb-3">
                                    <label for="dien-vien" class="form-label">Diễn viên</label> <input
                                        type="text" class="form-control" name="actor"
                                        id="dien-vien" placeholder="Nhập tên diễn viên">
                                </div>
                                <div class="mb-3">
                                    <label for="category" class="form-label">Thể loại</label>
                                    <select id="category" class="form-select" name="categoryId">
                                        <option value="none" selected disabled hidden>
                                            --Chọn thể loại--
                                        </option>

                                        <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category.id}">${category.name}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Giá</label> <input type="text"
                                                                                 class="form-control"
                                                                                 oninput="formatPrice(this)"
                                                                                 name="price"
                                                                                 placeholder="Nhập giá phim">
                                </div>
                                <div class="mb-3">
                                    <label for="noted" class="form-label">Mô tả</label>
                                    <textarea class="form-control" id="noted" name="description"
                                              rows="5"></textarea>
                                </div>

                                <input type="hidden" class="form-control" id="poster"
                                       name="poster">

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

<%@ include file="/views/admin/common/footer.jsp" %>

<script type="text/javascript">

    // TODO: delete, generate poster by Java code
    function fillHrefOnPoster() {
        var href = document.getElementById("link-phim").value;
        var poster = document.getElementById("poster");
        poster.value = "https://img.youtube.com/vi/" + href
            + "/maxresdefault.jpg";
    }
</script>
</body>
</html>
