<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/20/2023
  Time: 2:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Đăng Ký</title>
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
                    <h2>Đăng Ký</h2>
                    <p>Chào mừng bạn đến với website chính thức của MOVIE X</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Signup Section Begin -->
<section class="signup">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-12 col-lg-6">
                <div class="login__form">
                    <h3>Đăng ký</h3>
                    <form id="register-form-submit" onsubmit="return validateRegisterForm()" action="${initParam.mvcPath}/register"
                          method="POST">
                        <div class="input__item">
                            <i class="fa-solid fa-envelope"></i>
                            <input type="text" name="email" placeholder="Địa chỉ Email"/>

                        </div>
                        <div class="input__item">
                            <i class="fa-solid fa-user"></i>
                            <input type="text" name="fullName" placeholder="Họ và tên"/>

                        </div>
                        <div class="input__item">
                            <i class="fa-solid fa-phone"></i>
                            <input type="text" name="phone" placeholder="Số điện thoại"/>

                        </div>
                        <div class="input__item">
                            <i class="fa-solid fa-lock"></i>
                            <input type="password" name="password" placeholder="Mật khẩu"/>

                        </div>
                        <div class="input__item">
                            <i class="fa-solid fa-lock"></i>
                            <input type="password" name="passwordConfirm"
                                   placeholder="Nhập lại mật khẩu">

                        </div>
                        <button type="submit" id="submitRegister" class="site-btn">Xác
                            nhận
                        </button>
                    </form>
                    <h5>
                        Bạn đã có tài khoản ? <a href="${initParam.mvcPath}/login">Đăng nhập ngay</a>
                    </h5>
                </div>
            </div>
            <div class="col-12 col-md-12 col-lg-6">
                <div class="login__social__links">
                    <h3>Đăng nhập với:</h3>
                    <ul>
                        <li><a
                                href="${initParam.mvcPath}/oauth2/login/google"
                                class="google"><i class="fa-brands fa-google"></i> Đăng nhập với
                            Google</a></li>
                        <li><a href="#" class="facebook"><i class="fa-brands fa-facebook-f"></i> Đăng nhập với Facebook</a></li>
                        <li><a href="#" class="twitter"><i class="fa-brands fa-twitter"></i>
                            Đăng nhập với Twitter</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Signup Section End -->

<%@ include file="/views/user/common/footer.jsp" %>

<c:if test="${not empty sessionScope.existEmail}">
    <c:if test="${sessionScope.existEmail}">
        <script>
            showCenterAlert('error', 'Thất bại', 'Địa chỉ Email đã tồn tại trong cơ sở dữ liệu');
        </script>
    </c:if>
    <c:remove var="existEmail" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.existPhone}">
    <c:if test="${sessionScope.existPhone}">
        <script>
            showCenterAlert('error', 'Thất bại', 'Số điện thoại đã tồn tại trong cơ sở dữ liệu');
        </script>
    </c:if>
    <c:remove var="existPhone" scope="session"/>
</c:if>

</body>
</html>
