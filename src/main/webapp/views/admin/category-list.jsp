<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/10/2023
  Time: 10:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrator - Danh Sách Thể loại</title>

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
                    <a class="btn btn-primary float-end" href="${initParam.mvcPath}/admin/category/add"
                       role="button">
                        <i class="ti ti-file-plus"></i> Thêm mới
                    </a>
                    <h5 class="card-title fw-semibold mb-4 mt-2">Danh sách thể loại phim</h5>
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th scope="col">STT</th>
                                        <th scope="col">Thể loại</th>
                                        <th scope="col">Thời điểm tạo </th>
                                        <th scope="col">Hành động</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                                    <c:forEach items="${categories}" var="category" varStatus="loop">
                                        <tr>
                                            <td scope="row">${loop.index + 1}</td>
                                            <td>${category.name}</td>

                                            <fmt:formatDate value="${category.createdAt}" pattern="hh:mm:ss dd-MM-yyyy"
                                                            var="formattedDate"/>
                                            <td>${formattedDate}</td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <a class="btn btn-primary ms-2 rounded-2"
                                                       onclick=""
                                                       href="${initParam.mvcPath}/admin/category/edit?code=${category.code}"
                                                    >
                                                        Sửa
                                                    </a>
                                                    <button class="btn btn-danger ms-2 rounded-2"
                                                            onclick="submitDeleteForm()">Xoá
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>


                                        <form id="delete-category-form"
                                              action="${initParam.mvcPath}/admin/category/delete" method="post">
                                            <input type="hidden" name="code" value="${category.code}">
                                        </form>

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

<%@ include file="/views/admin/common/footer.jsp" %>

<c:if test="${not empty sessionScope.addCategorySuccess}">
    <c:choose>
        <c:when test="${sessionScope.addCategorySuccess}">
            <script>
                showSwalAlert('success', 'Thêm thể loại thành công');
            </script>
        </c:when>
        <c:otherwise>
            <script>
                showCenterAlert('error', 'Thất Bại', 'Thể loại đã tồn tại trong cơ sở dữ liệu');
            </script>
        </c:otherwise>
    </c:choose>
    <c:remove var="addCategorySuccess" scope="session"/>
</c:if>


<c:if test="${not empty sessionScope.updateCategorySuccess}">
    <c:choose>
        <c:when test="${sessionScope.updateCategorySuccess}">
            <script>
                showCenterAlert('success', 'Thông báo', 'Chỉnh sửa phim thành công');
            </script>
        </c:when>
    </c:choose>
    <c:remove var="updateCategorySuccess" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.deleteCategorySuccess}">
    <c:choose>
        <c:when test="${sessionScope.deleteCategorySuccess}">
            <script>
                showSwalAlert('success', 'Xoá video thành công');
            </script>
        </c:when>
    </c:choose>
    <c:remove var="deleteCategorySuccess" scope="session"/>
</c:if>

<script type="text/javascript">
    const deleteForm = document.querySelector("#delete-category-form");
    const submitDeleteForm = () => {
        Swal.fire({
            title: 'Xác nhận',
            text: "Bạn có chắc chắn muốn xoá thể loại này không?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý'
        }).then(result => {
            if (result.isConfirmed) {
                deleteForm.submit();
            }
        })

    };


</script>
</body>
</html>
