<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/23/2023
  Time: 11:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp"%>

<jsp:useBean id="user" scope="request" type="com.filmweb.dto.UserDto"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam['website-name']} - Trang Cá Nhân</title>
    <%@ include file="/views/common/head.jsp" %>
</head>


<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Normal Breadcrumb Begin -->
<section class="normal-breadcrumb set-bg"
         data-setbg="${pageContext.request.contextPath}/views/template/user/img/login-banner.png">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2>Trang cá nhân</h2>
                    <p>Chào mừng bạn đến với website chính thức của MOVIE X</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Login Section Begin -->
<section class="login spad container">
    <div class="rounded-lg">
        <div class="row bg-white p-5 m-1" style="border-radius: 6px">

            <div class="col-12 col-md-3 col-lg-3">

                <div class="img-profile">

                    <img src="${pageContext.request.contextPath}/views/template/user/img/avt/avt-${user.avtId}.jpg"
                         class="img-fluid rounded" width="60%" alt="avatar"/>

                </div>

                <div class="link-profile mt-4">
                    <h6>
                        <a style="color: #D14A2D;" href="${initParam['mvcPath']}/history" class="font-weight-bold">Lịch sử
                            truy cập</a>
                    </h6>
                    <h6 class="text-dark font-weight-bold mb-2">
                        <a style="color: #D14A2D;" href="${initParam['mvcPath']}/favorite" class="font-weight-bold">Phim
                            yêu thích</a>
                    </h6>

                </div>

            </div>

            <div class="col-12 col-md-9 col-lg-9">

                <div class="profile-name">

                    <div class="row">

                        <div class="col-12 col-md-12 col-lg-9">

                            <h4 class="text-dark font-weight-bold">Movie X</h4>
                            <span style="color: #D14A2D;" class="font-weight-bold">BLOG Xem Phim
									</span>

                        </div>

                        <div class="col-lg-3">

                            <h4>
                                <a style="color: #D14A2D;" href="${initParam['mvcPath']}/profile/edit"
                                   class="font-weight-bold text-decoration-none h6">
                                    Chỉnh sửa thông tin </a>
                            </h4>

                        </div>

                    </div>

                    <hr class="text-dark">

                </div>

                <div class="info mt-5">

                    <h4 class="text-dark mb-3 font-weight-bold">Thông tin</h4>

                    <div class="row">

                        <div class="col-lg-6">

                            <h6 class="text-dark mt-4">Họ và tên:</h6>
                            <h6 class="text-dark mt-4">Email:</h6>
                            <h6 class="text-dark mt-4">Số điện thoại:</h6>
                        </div>

                        <div class="col-lg-6">

                            <h6 class="text-dark mt-4">${user.fullName}</h6>
                            <h6 class="text-dark mt-4">${user.email}</h6>
                            <h6 class="text-dark mt-4">${user.phone}</h6>

                        </div>

                    </div>

                </div>

            </div>

        </div>
    </div>
</section>
<!-- Forgotpass Section End -->

<%@ include file="/views/common/footer.jsp" %>
</body>
</html>
