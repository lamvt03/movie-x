<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/23/2023
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<jsp:useBean id="email" scope="request" type="java.lang.String"/>
<jsp:useBean id="fullName" scope="request" type="java.lang.String"/>
<jsp:useBean id="phone" scope="request" type="java.lang.String"/>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chỉnh Sửa Trang Cá Nhân</title>
    <%@ include file="/views/common/head.jsp" %>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body>
<%@ include file="/views/common/header.jsp"%>

<!-- Normal Breadcrumb Begin -->
<section class="normal-breadcrumb set-bg"
         data-setbg="views/template/user/img/normal-breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2>Trang Cá Nhân</h2>
                    <p>Chào Mừng Bạn Đến Blog Chính Thức Của FilmViet.</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Login Section Begin -->
<section class="login spad container">
    <div class="row bg-white rounded-4 p-5 m-1"
         style="box-shadow: 4px 4px 4px rgba(190, 190, 190, 0.5); border-radius: 6px">

        <div class="col-12 col-md-3 col-lg-3">

            <div class="img-profile">

                <img src="${pageContext.request.contextPath}/views/template/user/img/profile-logo.png"
                     class="img-fluid rounded-top" width="60%">

            </div>

            <div class="link-profile">

                <h6 class="text-secondary font-weight-bold">WORD LINK</h6>
                <h6 class="text-dark mt-4 font-weight-bold mb-2">Website Link</h6>
                <h6 class="text-dark font-weight-bold mb-2">Bootsnipp Profile</h6>
                <h6 class="text-dark font-weight-bold mb-2">Bootply Profile</h6>

                <h6 class="text-secondary mt-4 font-weight-bold">SKILLS</h6>
                <h6 class="text-dark mt-4 font-weight-bold mb-2">Web Designer</h6>
                <h6 class="text-dark font-weight-bold mb-2">Web Developer</h6>
                <h6 class="text-dark font-weight-bold mb-2">WordPress</h6>
                <h6 class="text-dark font-weight-bold mb-2">WooCommere</h6>
                <h6 class="text-dark font-weight-bold mb-2">PHP.Net</h6>
                <h6 class="text-dark font-weight-bold mb-2">XARM</h6>
                <h6 class="text-dark font-weight-bold mb-2">SQL Server</h6>

            </div>

        </div>

        <div class="col-12 col-md-9 col-lg-9">

            <div class="profile-name">

                <div class="row">

                    <div class="col-6 col-md-8 col-lg-9">

							<span> <a href="${initParam['mvcPath']}/profile"
                                      class="text-info fs-6 text-decoration-none font-weight-bold ">
									<i class="bi bi-chevron-left text-dard"></i> Quay về
							</a>
							</span>

                    </div>

                    <div class="col-6 col-md-4 col-lg-3">

							<span> <a href="${initParam['mvcPath']}/password/change"
                                      class="text-info fs-6 text-decoration-none font-weight-bold">
									Đổi mật khẩu </a>
							</span>

                    </div>

                </div>

                <hr class="text-dark">

            </div>

            <div class="info mt-5">

                <h4 class="text-dark mb-4 font-weight-bold">Thông tin người
                    dùng</h4>

                <div class="row">

                    <div class="col-12 col-md-12 col-lg-12">

                        <form id="form" onsubmit="return checkEditProfile()"
                              autocomplete="off" action="${initParam['mvcPath']}/profile/edit" method="post">

                            <fieldset disabled>
                                <div class="mb-4">
                                    <input type="text" class="form-control disable"
                                           value="${email}" name="email" placeholder="email">
                                </div>
                            </fieldset>

                            <div class="mb-4">
                                <input type="text" class="form-control" value="${fullName}"
                                       name="fullname" placeholder="Họ và tên" />
                            </div>

                            <div class="mb-4">
                                <input type="text" class="form-control" value="${phone}"
                                       name="phone" placeholder="Số điện thoại">
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

<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
