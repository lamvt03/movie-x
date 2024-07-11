<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/11/2023
  Time: 6:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/views/common/taglib.jsp" %>

<!-- Footer Section Begin -->
<footer class="footer">
    <div class="page-up">
        <a href="#" id="scrollToTopButton"><i class="fa-solid fa-angle-up"></i></a>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <div class="footer__logo">
                    <a href="${initParam.mvcPath}/home"><img
                            src="${pageContext.request.contextPath}/views/user/assets/img/logo.png"
                            alt="logo"></a>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="footer__nav">
                    <ul>
                        <li class="active"><a href="${initParam.mvcPath}/home">Trang Chủ</a></li>
                        <li><a href="${initParam.mvcPath}/all">Danh Sách Phim</a></li>
                        <li><a href="${initParam.mvcPath}/about">Về Chúng Tôi</a></li>
                        <c:if test="${not empty sessionScope.currentUser}">
                            <li><a href="${initParam.mvcPath}/profile">Trang Cá Nhân</a></li>
                        </c:if>

                    </ul>
                </div>
            </div>
            <div class="col-lg-12">
                <p class="text-center">
                    Copyright &copy;
                    <script>
                        document.write(new Date().getFullYear());
                    </script>
                    All rights reserved | Design and Developed by Vo Truong Lam
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
            <i class="fa-solid fa-xmark"></i>
        </div>
        <form action="${initParam.mvcPath}/search" method="get" class="search-model-form"
              autocomplete="off">
            <input type="text" name="keyword" id="search-input"
                   placeholder="Tìm kiếm..."/>
        </form>
    </div>
</div>
<!-- Search model end -->

<!-- Js Plugins -->
<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/sweetalert2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/plyr.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/jquery.nice-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/mixitup.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/jquery.slicknav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/main.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/validateUser.js"></script>

<!-- đăng nhập thành công -->
<c:if test="${not empty sessionScope.loginSuccess}">
    <c:choose>
        <c:when test="${sessionScope.loginSuccess}">
            <script>
                showTopEndAlert('success', 'Đăng nhập thành công');
            </script>
        </c:when>
        <c:otherwise>
            <script>
                showTopEndAlert('error', 'Tên tài khoản hoặc mật khẩu không chính xác');
            </script>
        </c:otherwise>
    </c:choose>
    <c:remove var="loginSuccess" scope="session" />
</c:if>


<c:if test="${not empty sessionScope.emailNotVerified}">
    <c:if test="${sessionScope.emailNotVerified}">
        <script>
            showTopEndAlert('warning', 'Tài khoản chưa được xác minh email');
        </script>
    </c:if>
    <c:remove var="emailNotVerified" scope="session"/>
</c:if>





