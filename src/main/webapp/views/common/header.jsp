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
                            <li>
                                <a href="#">
                                    <i class="fa-sharp fa-solid fa-bars"></i> Thể Loại <i class="fa-solid fa-angle-down"></i>
                                </a>
                                <ul class="dropdown">
                                    <li><a href="${initParam['mvcPath']}/phim-hanh-dong">Phim hành động</a></li>
                                    <li><a href="${initParam['mvcPath']}/phim-vien-tuong">Phim viễn tưởng</a></li>
                                    <li><a href="${initParam['mvcPath']}/phim-co-trang">Phim cổ trang</a></li>
                                    <li><a href="${initParam['mvcPath']}/phim-kinh-di">Phim kinh dị</a></li>
                                    <li><a href="${initParam['mvcPath']}/phim-tam-ly">Phim tâm lý</a></li>
                                </ul>
                            </li>
                            <li><a href="${initParam['mvcPath']}/about">
                                <i class="fa-solid fa-circle-info"></i>
                                Giới Thiệu</a>
                            </li>
                            <li><a style="cursor: pointer;" class="search-switch"><i
                                    class="fa-solid fa-magnifying-glass"></i> Tìm Kiếm</a></li>
                            <c:choose>
                                <c:when test="${not empty sessionScope.currentUser}">
                                    <li><a href="#"> <span class="wave">👋</span> Xin chào
                                        ! ${sessionScope.currentUser.fullName} <i class="fa-solid fa-angle-down"></i>
                                    </a>
                                        <ul class="dropdown">
                                            <li><a href="${initParam['mvcPath']}/profile">Trang cá nhân</a></li>
                                            <li><a href="${initParam['mvcPath']}/transaction">Lịch sử giao dịch</a></li>
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