
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/23/2023
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<jsp:useBean id="user" scope="request" type="com.moviex.dto.UserDto"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Đổi Mật Khẩu</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Normal Breadcrumb Begin -->
<section class="normal-breadcrumb set-bg"
         data-setbg="${pageContext.request.contextPath}/views/user/assets/img/login-banner.jpg">
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
    <div class="row bg-white rounded-4 p-5 m-1"
         style="box-shadow: 4px 4px 4px rgba(190, 190, 190, 0.5); border-radius: 6px;">

        <div class="col-12 col-md-3 col-lg-3">

            <div class="img-profile">
                <img src="${user.image}" class="rounded-top img-fluid" />
            </div>

            <div class="link-profile">

                <h6 class="text-secondary text-center mb-2">${user.fullName}</h6>
                <h6 class="text-secondary text-center mb-2">${user.email}</h6>
                <h6 class="text-secondary text-center">
                    <img src="${pageContext.request.contextPath}/views/user/assets/img/vn.png" class="img-fluid"
                         width="20px" alt=""> Việt Nam
                </h6>

            </div>

        </div>

        <div class="col-12 col-md-9 col-lg-9">

            <div class="profile-name">

                <hr class="text-dark">

            </div>

            <div class="info mt-5">

                <h4 class="text-dark mb-4 font-weight-bold">Đổi mật khẩu</h4>

                <div class="row">

                    <div class="col-12 col-md-12 col-lg-12">

                        <form id="form" autocomplete="off"
                              onsubmit="return validateChangePass()" action="${initParam.mvcPath}/password/change"
                              method="post">


                            <fieldset disabled>
                                <div class="mb-4">
                                    <input type="text" class="form-control disable"
                                           value="${user.email}" placeholder="username" />
                                </div>
                            </fieldset>

                            <div class="mb-4">
                                <input type="password" class="form-control" name="oldPass"
                                       placeholder="Mật khẩu cũ">
                            </div>

                            <div class="mb-4">
                                <input type="password" class="form-control" name="newPass"
                                       placeholder="Mật khẩu mới">
                            </div>

                            <div class="mb-4">
                                <input type="password" class="form-control" name="cofirmPass"
                                       placeholder="Nhập lại mật khẩu">
                            </div>

                            <input type="hidden" name="confirmation" id="confirmationField"
                                   value="false" />

                            <button type="submit" class="btn btn-outline-success">Lưu
                                thông tin</button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>
</section>
<!-- Forgotpass Section End -->
<%@ include file="/views/user/common/footer.jsp" %>

</body>
</html>
