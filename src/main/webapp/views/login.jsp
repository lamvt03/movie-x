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
    <title>${initParam['website-name']} - Đăng nhập</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Normal Breadcrumb Begin -->
<section class="normal-breadcrumb set-bg"
         data-setbg="${pageContext.request.contextPath}/views/template/user/img/login-banner.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2>Đăng Nhập</h2>
                    <p>Chào mừng bạn đến với website chính thức của MOVIE X</p>
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
                    <h3>Đăng nhập</h3>
                    <form onsubmit="return validateLoginForm()" action="${initParam['mvcPath']}/login"
                          method="POST">
                        <div class="input__item">
                            <i class="fa-solid fa-envelope"></i>
                            <input type="text" name="email" placeholder="Tên đăng nhập"/>
                        </div>
                        <div class="input__item">
                            <i class="fa-solid fa-lock"></i>
                            <input type="password" name="password" placeholder="Mật khẩu">
                        </div>
                        <button type="submit" class="site-btn">Đăng nhập ngay</button>
                    </form>
                    <a href="${initParam['mvcPath']}/password/forgot" class="forget_pass">Quên mật khẩu ?</a>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="login__register">
                    <h3>Chưa có tài khoản ?</h3>
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
                            <li><a href="#" class="facebook"><i class="fa-brands fa-facebook-f"></i> Đăng nhập với
                                Facebook</a></li>
                            <li><a
                                    href="#"
                                    class="google"><i class="fa-brands fa-google"></i> Đăng nhập với
                                Google</a></li>
                            <li><a href="#" class="twitter"><i class="fa-brands fa-twitter"></i> Đăng nhập với
                                Twitter</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Login Section End -->

<%@ include file="/views/common/footer.jsp" %>

<c:if test="${not empty sessionScope.changePassSuccess}">
    <c:if test="${sessionScope.changePassSuccess}">
        <script>
            showCenterAlert('success', 'Thành Công !', 'Lấy lại mật khẩu thành công !');
        </script>
    </c:if>

    <%-- Remove the attribute after displaying the message --%>
    <c:remove var="changePassSuccess" scope="session"/>
</c:if>

<%-- Remove the "otp" attribute --%>
<c:remove var="otp" scope="session"/>


<c:if test="${not empty sessionScope.newPassSuccess}">
    <c:if test="${sessionScope.newPassSuccess}">
        <script>
            showCenterAlert('success', 'Thành Công', 'Thay đổi mật khẩu thành công');
        </script>
    </c:if>

    <%-- Remove the attribute after displaying the message --%>
    <c:remove var="newPassSuccess" scope="session"/>
</c:if>

<script type="text/javascript">
    function validateLoginForm() {
        const emailRegex = /\b[\w.%-]+@[-.\w]+\.[A-Za-z]{2,4}\b/;
        const PASSWORD_LENGTH = 6;

        const email = document.getElementsByName("email")[0].value;
        const password = document.getElementsByName("password")[0].value;

        if (!email) {
            showSwalAlert('error', 'Vui lòng nhập địa chỉ Email !');
            return false;
        }
        if (!emailRegex.test(email)) {
            showSwalAlert('error', 'Địa chỉ Email không đúng định dạng !');
            return false;
        }
        if (!password) {
            showSwalAlert('error', 'Vui lòng nhập mật khẩu !');
            return false;
        }
        if (password.length < PASSWORD_LENGTH) {
            showSwalAlert('error', 'Mật khẩu phải có ít nhất 6 ký tự !');
            return false;
        }
        return true;
    }
</script>
</body>
</html>
