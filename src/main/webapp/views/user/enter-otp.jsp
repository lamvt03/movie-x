<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/22/2023
  Time: 12:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Quên mật khẩu</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>

<%@ include file="/views/user/common/header.jsp" %>

<section class="normal-breadcrumb set-bg"
         data-setbg="${pageContext.request.contextPath}/views/user/assets/img/login-banner.jpg">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2>Quên Mật Khẩu</h2>
                    <p>Chào Mừng Bạn Đến Blog Chính Thức Của Movie X</p>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Normal Breadcrumb End -->

<!-- Login Section Begin -->
<section class="login spad">
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-12 col-lg-12">
                <div class="card-forgot card p-3 mb-4 rounded-0"
                     style="color: grey;">
                    <h3 class="mb-2" style="font-weight: 700; color: grey;">Quên
                        Mật Khẩu ?</h3>
                    <p class="mb-3">thay đổi mật khẩu của bạn trong ba bước đơn
                        giản. Điều này sẽ giúp bạn bảo mật mật khẩu của bạn.</p>

                    <div class="step_form" style="color: grey;">
                        <p>
                            <Strong class="text-info">1</Strong> .Nhập địa chỉ Email của
                            bạn. Hệ thống của sẽ gửi cho bạn một mã OTP vào email của bạn. <br>
                            <Strong class="text-info">2</Strong> <strong>.Nhập mã
                            OTP đã nhận được ở email của bạn. </strong><br> <Strong
                                class="text-info">3</Strong> .Tạo một mật khẩu mới.
                        </p>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-12 col-lg-12">
                <div class="card-forgot card p-3 rounded-0" style="color: grey;">
                    <div class="step_form" style="color: grey;">
                        <form onsubmit="return validateEnterOTP()" action="${initParam.mvcPath}/otp/validate"
                              method="post">
                            <div class="mb-3">
                                <label for="otp" class="form-label">Nhập Mã OTP: </label> <input
                                    type="text" class="form-control" name="otp" id="otp"/>
                                <small id="helpId" class="form-text text-muted">Nhập mã
                                    OTP hệ thống đã gửi cho bạn trong hòm thư điện tử !</small>

                            </div>
                            <hr>
                            <button type="submit"
                                    class="btn btn-success rounded-0 p-2 ps-4 px-4 fw-bold text-white">
                                TIẾP TỤC
                            </button>
                            <a type="button" href="${initParam.mvcPath}/login"
                               class="btn btn-danger rounded-0 p-2 ps-4 px-4 fw-bold  text-white"
                               role="button">TRỞ LẠI</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Forgotpass Section End -->

<%@ include file="/views/user/common/footer.jsp" %>

<script type="text/javascript">
    const OTP_LENGTH = 6;
    const regexOTP = /^\d+$/;

    const validateEnterOTP = () => {

        const otp = document.querySelector("#otp").value;

        if (!otp) {
            showTopEndAlert('error', 'Vui lòng nhập mã OTP');
            return false;
        }

        if (!regexOTP.test(otp)) {
            showTopEndAlert('error', 'Mã OTP không đúng định dạng');
            return false;
        }

        if (otp.length !== OTP_LENGTH) {
            showTopEndAlert('error', 'Mã OTP phải có 6 kí tự');
            return false;
        }
        return true;
    }
</script>

<c:if test="${not empty sessionScope.errorOTP}">
    <c:if test="${sessionScope.errorOTP}">
        <script>
            showTopEndAlert('error', 'Mã OTP không chính xác hoặc đã hết hạn');
        </script>
    </c:if>
    <c:remove var="errorOTP" scope="session"/>
</c:if>

</body>
</html>
