<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/11/2023
  Time: 6:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!-- Footer Section Begin -->
<footer class="footer">
    <div class="page-up">
        <a href="#" id="scrollToTopButton"><span class="arrow_carrot-up"></span></a>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="footer__logo">
                    <a href="${initParam['mvcPath']}/home"><img src="${pageContext.request.contextPath}/views/template/user/img/logo.png"
                                             alt=""></a>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="footer__nav">
                    <ul>
                        <li class="active"><a href="${initParam['mvcPath']}/home">Trang Chủ</a></li>
                        <li><a href="categories">Danh Sách Phim</a></li>
                        <li><a href="${initParam['mvcPath']}/about">Liên Hệ Với Tôi</a></li>
                        <li><a href="${initParam['mvcPath']}/profile">Trang Cá Nhân</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-12">
                <p class="text-center">
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    Copyright &copy;
                    <script>
                        document.write(new Date().getFullYear());
                    </script>
                    All rights reserved
                </p>

            </div>
        </div>
    </div>
</footer>
<!-- Footer Section End -->

<!-- Search model Begin -->
<div class="search-model">
    <div class="h-100 d-flex align-items-center justify-content-center">
        <div class="search-close-switch">
            <i class="icon_close"></i>
        </div>
        <form action="${initParam['mvcPath']}/search" method="get" class="search-model-form"
              autocomplete="off">
            <input type="text" name="keyword" id="search-input"
                   placeholder="Tìm kiếm.....">
        </form>
    </div>
</div>
<!-- Search model end -->

<!-- Js Plugins -->
<%--<script src="sweetalert2.min.js"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/player.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/mixitup.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/jquery.slicknav.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/main.js"></script>
<script src="${pageContext.request.contextPath}/views/template/user/js/validateUser.js"></script>

<!-- đăng nhập thành công -->
<%  Boolean loginSuccess = (Boolean) session.getAttribute("loginSuccess");
    if (loginSuccess != null) {
        if (loginSuccess) {
%>
<script>
    showSwalAlert('success', 'Đăng nhập thành công!');
</script>

<%
        } else {
%>
<script>
    showSwalAlert('error', 'Tên tài khoản hoặc mật khẩu không chính xác');
</script>

<%       }
        session.removeAttribute("loginSuccess");
    }
%>

<%
    Boolean loginFail = (Boolean) session.getAttribute("loginFail");
    if (loginFail != null) {
        if (loginFail) {
%>
<script>
    showSwalAlert('warning', 'Tài khoản không hoạt động !');
</script>
<%      }
        session.removeAttribute("loginFail");
    }
%>

<% Boolean registerSuccess = (Boolean) session.getAttribute("registerSuccess");
    if (registerSuccess != null) {
        if (registerSuccess) {
%>
<script>
    showCenterAlert('success', 'Thành công !',
        'Một email xác minh đã gửi đến địa chỉ email của bạn !');
</script>

<% }
    session.removeAttribute("registerSuccess");
}
%>



