<jsp:useBean id="category" scope="request" type="com.filmweb.entity.Category"/>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/11/2023
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Administrator - Thêm phim mới</title>

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
                    <h5 class="card-title fw-semibold mb-4 mt-2">Thêm Thể Loại Mới</h5>
                    <div class="card">
                        <div class="card-body">
                            <form onsubmit="" action="${initParam['mvcPath']}/admin/category/edit"
                                  method="post">

                                <div class="mb-3">
                                    <label for="name" class="form-label">Tên thể loại</label> <input
                                        type="text" class="form-control" name="name" id="name"
                                        value="${category.name}"
                                        placeholder="Nhập tên phim">
                                </div>
                                <div class="mb-3">
                                    <label for="code" class="form-label">Code phim</label> <input
                                        type="text" class="form-control bg-dark-light text-white" name="code" id="code"
                                        value="${category.code}"
                                        placeholder="Code thể loại" readonly>
                                </div>


                                <button type="submit" class="btn btn-success">Xác nhận</button>
                                <a class="btn btn-danger float-end" href="${initParam['mvcPath']}/admin/categories"
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

<script type="text/javascript">
    const slugify = (str) =>  String(str)
        .normalize('NFKD') // split accented characters into their base characters and diacritical marks
        .replace(/[\u0300-\u036f]/g, '') // remove all the accents, which happen to be all in the \u03xx UNICODE block.
        .trim() // trim leading or trailing whitespace
        .toLowerCase() // convert to lowercase
        .replace(/[^a-z0-9 -]/g, '') // remove non-alphanumeric characters
        .replace(/\s+/g, '-') // replace spaces with hyphens
        .replace(/-+/g, '-'); // remove consecutive hyphens
    const nameInput = document.querySelector("#name");
    const codeInput = document.querySelector("#code");
    nameInput.oninput = (e) => {
        codeInput.value = slugify(e.target.value);
    }
</script>
</body>
</html>
