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
    <title>${initParam['website-name']} - Đăng Ký</title>
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
                    <h2>Đăng Ký</h2>
                    <p>Chào mừng bạn đến với website chính thức của MOVIE W3B</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Signup Section Begin -->
<section class="signup spad">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-12 col-lg-6">
                <div class="login__form">
                    <h3>Đăng ký</h3>
                    <form id="register-form-submit" onsubmit="return validateRegisterForm()" action="${initParam['mvcPath']}/register"
                          method="POST">
                        <div class="input__item">
                            <input type="text" name="email" placeholder="Địa chỉ Email">
                            <span class="icon_mail"></span>
                        </div>
                        <div class="input__item">
                            <input type="text" name="fullName" placeholder="Họ và tên">
                            <span class="icon_profile"></span>
                        </div>
                        <div class="input__item">
                            <input type="text" name="phone" placeholder="Số điện thoại">
                            <span class="icon_phone"></span>
                        </div>
                        <div class="input__item">
                            <input type="password" name="password" placeholder="Mật khẩu">
                            <span class="icon_lock"></span>
                        </div>
                        <div class="input__item">
                            <input type="password" name="passwordConfirm"
                                   placeholder="Nhập lại mật khẩu"> <span
                                class="icon_lock"></span>
                        </div>
                        <button type="submit" id="submitRegister" class="site-btn">Xác
                            nhận
                        </button>
                    </form>
                    <h5>
                        Bạn đã có tài khoản ? <a href="${initParam['mvcPath']}/login">Đăng nhập ngay</a>
                    </h5>
                </div>
            </div>
            <div class="col-12 col-md-12 col-lg-6">
                <div class="login__social__links">
                    <h3>Đăng nhập với:</h3>
                    <ul>
                        <li><a href="#" class="facebook"><i class="fa-brands fa-facebook-f"></i> Đăng nhập với Facebook</a></li>
                        <li><a
                                href="#"
                                class="google"><i class="fa-brands fa-google"></i> Đăng nhập với
                            Google</a></li>
                        <li><a href="#" class="twitter"><i class="fa-brands fa-twitter"></i>
                            Đăng nhập với Twitter</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Signup Section End -->

<%@ include file="/views/common/footer.jsp" %>

<%
    if (session.getAttribute("existEmail") != null && (boolean) session.getAttribute("existEmail")) {
%>
<script>
    showCenterAlert('error', 'Thất bại !', 'Địa chỉ Email đã tồn tại trong cơ sở dữ liệu !');
</script>
<%
    }
    else if (session.getAttribute("existPhone") != null && (boolean) session.getAttribute("existPhone"))  {
%>
<script>
    showCenterAlert('error', 'Thất bại !', 'Số điện thoại đã tồn tại trong cơ sở dữ liệu !');
</script>
<%
    }
    session.removeAttribute("existPhone");
    session.removeAttribute("existEmail");
%>
</body>
</html>
