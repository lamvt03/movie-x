<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/20/2023
  Time: 1:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>
<html lang="en">
<head>
    <title>Đăng nhập</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Normal Breadcrumb Begin -->
<section class="normal-breadcrumb set-bg"
         data-setbg="${pageContext.request.contextPath}/views/template/user/img/normal-breadcrumb.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2>Đăng Nhập</h2>
                    <p>Chào mừng bạn đến với website chính thức của XXX</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Login Section Begin -->
<section class="login">
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="login__form">
                    <h3>Đăng Nhập</h3>
                    <form onsubmit="return validateLoginForm()" action="${initParam['mvcPath']}/login"
                          method="POST">
                        <div class="input__item">
                            <input type="text" name="email" placeholder="Email" /> <span
                                class="icon_mail"></span>
                        </div>
                        <div class="input__item">
                            <input type="password" name="password" placeholder="Mật khẩu">
                            <span class="icon_lock"></span>
                        </div>
                        <button type="submit" class="site-btn">Đăng Nhập Ngay</button>
                    </form>
                    <a href="${initParam['mvcPath']}/password/forgot" class="forget_pass">Quên mật khẩu ?</a>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="login__register">
                    <h3>Bạn chưa có tài khoản ?</h3>
                    <a href="${initParam['mvcPath']}/register" class="primary-btn">Đăng ký ngay</a>
                </div>
            </div>
        </div>
        <div class="login__social">
            <div class="row d-flex justify-content-center">
                <div class="col-lg-6">
                    <div class="login__social__links">
                        <span>Hoặc</span>
                        <ul>
                            <li><a href="#" class="facebook"><i
                                    class="fa fa-facebook"></i> Đăng nhập với Facebook</a></li>
                            <li><a
                                    href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/BackEnd/login&response_type=code
    &client_id=794386492125-ihgk5oo0mg850vefp61rctp97m3pede9.apps.googleusercontent.com&approval_prompt=force"
                                    class="google"><i class="fa fa-google"></i> Đăng nhập với
                                Google</a></li>
                            <li><a href="#" class="twitter"><i
                                    class="fa fa-twitter"></i> Đăng nhập với Twitter</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Login Section End -->

<%@ include file="/views/common/footer.jsp" %>

<%
    Boolean changePassSuccess = (Boolean) session.getAttribute("changePassSuccess");

    if (changePassSuccess != null) {
        if (changePassSuccess) {
%>
<script>
    showCenterAlert('success', 'Thành Công !',
        'Lấy lại mật khẩu thành công !');
</script>
<%
        }
        session.removeAttribute("changePassSuccess");
    }

    session.removeAttribute("otp");
%>

<%
    Boolean newPassSuccess = (Boolean) session.getAttribute("newPassSuccess");

    if (newPassSuccess != null) {
        if (newPassSuccess) {
%>
<script>
    showCenterAlert('success', 'Thành Công !',
        'Thay đổi mật khẩu thành công !');
</script>
<%
        }
        session.removeAttribute("newPassSuccess");
    }
%>
</body>
</html>
