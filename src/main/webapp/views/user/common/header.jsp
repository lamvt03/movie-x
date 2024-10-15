<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="/views/common/taglib.jsp" %>

<!-- Page Preloader -->
<div id="preloader">
    <div class="loader"></div>
</div>

<div class="loading-container invisible">
    <div class="loading-spinner"></div>
</div>

<!-- Header Section Begin -->
<header class="header">
    <div class="container container-md container-lg">
        <div class="row">
            <div class="col-lg-2">
                <div class="header__logo">
                    <a href="${initParam.mvcPath}/home">
                        <img src="${pageContext.request.contextPath}/views/user/assets/img/logo.png"
                             alt="Website logo"/>
                    </a>
                </div>
            </div>
            <div class="col-lg-10">
                <div class="header__nav">
                    <nav class="header__menu mobile-menu">
                        <ul>
                            <li class="${sessionScope.pathInfo == "/home" ? "active" : null}">
                                <a href="${initParam.mvcPath}/home">
                                    <i class="fa-solid fa-house"></i>
                                    Trang Chủ
                                </a>
                            </li>
                            <li class="${sessionScope.pathInfo == "/category" ? "active" : null}">
                                <a href="#">
                                    <i class="fa-sharp fa-solid fa-bars"></i> Thể Loại <i class="fa-solid fa-angle-down"></i>
                                </a>
                                <ul class="dropdown">
                                    <c:forEach items="${sessionScope.categories}" var="category">
                                        <li><a href="${initParam.mvcPath}/category/${category.slug}">${category.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                            <li class="${sessionScope.pathInfo == "/about" ? "active" : null}">
                                <a href="${initParam.mvcPath}/about">
                                    <i class="fa-solid fa-circle-info"></i>
                                    Giới Thiệu</a>
                            </li>
                            <li><a style="cursor: pointer;" class="search-switch"><i
                                    class="fa-solid fa-magnifying-glass"></i> Tìm Kiếm</a></li>
                            <c:choose>
                                <c:when test="${not empty sessionScope.currentUser}">
                                    <li><a href="#"> <span class="wave">👋</span> Xin chào, ${sessionScope.currentUser.fullName} <i class="fa-solid fa-angle-down"></i>
                                    </a>
                                        <ul class="dropdown">
                                            <li><a href="${initParam.mvcPath}/profile">Trang cá nhân</a></li>
                                            <li><a href="${initParam.mvcPath}/transaction">Lịch sử giao dịch</a></li>
                                            <c:if test="${sessionScope.currentUser.registrationType == 'INTERNAL'}">
                                                <li><a href="${initParam.mvcPath}/password/change">Đổi mật khẩu</a></li>
                                            </c:if>
                                            <li><a href="${initParam.mvcPath}/logout">Đăng xuất</a></li>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="${sessionScope.pathInfo == "/login" ? "active" : null}">
                                        <a href="${initParam.mvcPath}/login"><i class="fa-solid fa-user"></i>
                                        Đăng Nhập</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div id="mobile-menu-wrap"></div>
    </div>
</header>
<!-- Header End -->

<div style="margin-top: 60px;"></div>