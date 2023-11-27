<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- Page Preloader -->
<div id="preloader">
    <div class="loader"></div>
</div>

<!-- Header Section Begin -->
<header class="header">
    <div class="container">
        <div class="row">
            <div class="col-lg-2">
                <div class="header__logo">
                    <a href="${initParam['mvcPath']}/home">
                        <img src="${pageContext.request.contextPath}/views/template/user/img/logo.png"
                             alt="Website logo"/>
                    </a>
                </div>
            </div>
            <div class="col-lg-10">
                <div class="header__nav">
                    <nav class="header__menu mobile-menu">
                        <ul>
                            <li class="active">
                                <a href="${initParam['mvcPath']}/home">
                                    <i class="fa-solid fa-house"></i>
                                    Trang Chủ
                                </a>
                            </li>
                            <li><a href="categories"><i
                                    class="fa-sharp fa-solid fa-bars"></i> Thể Loại <span
                                    class="arrow_carrot-down"></span></a>
                                <ul class="dropdown">
                                    <li><a href="#">Phim Chiếu Rạp</a></li>
                                    <li><a href="#">Phim Việt Nam</a></li>
                                    <li><a href="#">Phim hành động</a></li>
                                    <li><a href="#">Phim gia đình</a></li>
                                    <li><a href="#">Phim hài hước</a></li>
                                    <li><a href="#">Phim kinh dị</a></li>
                                    <li><a href="#">Phim chiến tranh</a></li>
                                    <li><a href="#">Phim phiêu lưu</a></li>
                                    <li><a href="#">Phim tình cảm</a></li>
                                    <li><a href="#">Phim tâm lý</a></li>
                                    <li><a href="#">Phim tài liệu</a></li>
                                    <li><a href="#">Phim thần thoại</a></li>
                                </ul>
                            </li>
                            <li><a href="${initParam['mvcPath']}/about"><span class="icon_info"></span>
                                Giới Thiệu</a></li>
                            <li><a style="cursor: pointer;" class="search-switch"><span
                                    class="icon_search"></span> Tìm Kiếm</a></li>
                            <c:choose>
                                <c:when test="${not empty sessionScope.currentUser}">
                                    <li><a href="#"> <span class="wave">👋</span> Xin chào
                                        ! ${sessionScope.currentUser.fullName} <span
                                                class="arrow_carrot-down"></span>
                                    </a>
                                        <ul class="dropdown">
                                            <li><a href="profile">Trang cá nhân</a></li>
                                            <li><a href="transaction">Lịch sử giao dịch</a></li>
                                            <li><a href="${initParam['mvcPath']}/logout">Đăng xuất</a></li>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${initParam['mvcPath']}/login"><span class="icon_profile"></span>
                                        Đăng Nhập</a></li>
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