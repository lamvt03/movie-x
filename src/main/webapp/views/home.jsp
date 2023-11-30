<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07-Oct-23
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<jsp:useBean id="trendingVideos" scope="request" type="java.util.List"/>
<jsp:useBean id="videos" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Trang chủ</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Hero Section Begin -->
<section class="hero">
    <div class="container">
        <div class="hero__slider owl-carousel">

            <c:forEach items="${trendingVideos}" var="trendingVideo">
                <div class="hero__items set-bg" data-setbg="${trendingVideo.poster}">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="hero__text">
                                <div class="label">Tình Cảm Gia Đình</div>
                                <h2>${trendingVideo.title}</h2>
                                <p>Thể loại: ${trendingVideo.category}</p>
                                <c:choose>
                                    <c:when test="${trendingVideo.price == 0}">
                                        <a href="${initParam['mvcPath']}/video/watch?v=${trendingVideo.href}"><span>Xem
													Ngay</span> <i class="fa fa-angle-right"></i></a>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:setLocale value="vi_VN"/>
                                        <c:set var="formattedPrice" value="${trendingVideo.price}"/>
                                        <fmt:formatNumber var="formattedPrice"
                                                          value="${formattedPrice}" type="currency" currencyCode="VND"/>
                                        <a href="${initParam['mvcPath']}/video/watch?v=${trendingVideo.href}"
                                           class="watch-btn"><span>${formattedPrice}</span> <i
                                                class="fa fa-angle-right"></i></a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<!-- Hero Section End -->

<!-- Product Section Begin -->
<section class="product spad">

    <div class="container">

        <div class="row">

            <div class="col-lg-8">
                <div class="trending__product">
                    <div class="row">
                        <div class="col-lg-8 col-md-8 col-sm-8">
                            <div class="section-title">
                                <h4>XU HƯỚNG</h4>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4">
                            <div class="btn__all">
                                <a href="categories" class="primary-btn">Xem Tất Cả<span
                                        class="arrow_right"></span></a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <c:forEach items="${videos}" var="video">
                            <div class="col-lg-4 col-md-6 col-sm-6">
                                <div class="product__item">
                                    <a
                                            href="${initParam['mvcPath']}/video/detail?v=${video.href}">
                                        <div class="product__item__pic set-bg"
                                             data-setbg="${video.poster}">
                                            <!-- <div class="ep">18 / 18</div> -->
                                            <div class="comment">
                                                <i class="fa-solid fa-heart"></i> ${video.likeQuantity}
                                            </div>
                                            <div class="view" style="margin-right: 50px">
                                                <i class="fa-solid fa-comment"></i> ${video.commentQuantity }
                                            </div>
                                            <div class="view">
                                                <i class="fa fa-eye"></i> ${video.views }
                                            </div>
                                        </div>
                                    </a>
                                    <div class="product__item__text">
                                        <ul>
                                            <li>${video.category} - ${video.timeAgo}</li>
                                        </ul>
                                        <h5>
                                            <a href="#">${video.title }</a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="product__pagination d-flex justify-content-center">


                    <c:if test="${currentPage == 1}">
                        <a href="#"><i class="fa fa-angle-double-left"></i></a>
                    </c:if>

                    <c:if test="${currentPage > 1}">
                        <a href="${initParam['mvcPath']}/home?page=${currentPage - 1}"><i
                                class="fa fa-angle-double-left"></i></a>
                    </c:if>
                    <c:forEach varStatus="i" begin="1" end="${maxPage}">
                        <a href="${initParam['mvcPath']}/home?page=${i.index}" style="margin-left: 10px"
                           class="current-page ${currentPage == i.index ? 'active' : ''}">${i.index}</a>
                    </c:forEach>

                    <c:if test="${currentPage == maxPage}">
                        <a href="#"><i class="fa fa-angle-double-right"></i></a>
                    </c:if>
                    <c:if test="${currentPage < maxPage}">
                        <a href="${initParam['mvcPath']}/home?page=${currentPage + 1}"><i
                                class="fa fa-angle-double-right"></i></a>
                    </c:if>

                </div>
            </div>

            <div class="col-lg-4 col-md-6 col-sm-8">
                <div class="product__sidebar">
                    <div class="product__sidebar__view">
                        <div class="section-title">
                            <h5>TOP XEM HÀNG ĐẦU</h5>
                        </div>
                        <ul class="filter__controls">
                            <li class="active" data-filter="*">Ngày</li>
                            <li data-filter=".week">Tuần</li>
                            <li data-filter=".month">Tháng</li>
                            <li data-filter=".years">Năm</li>
                        </ul>
                        <div class="filter__gallery">
                            <div class="product__sidebar__view__item set-bg mix day years"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-1.jpg">
                                <div class="ep">1 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 9141
                                </div>
                                <h5>
                                    <a href="#">Mắt Biếc</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix month week"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-2.jpg">
                                <div class="ep">1 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 9141
                                </div>
                                <h5>
                                    <a href="#">Lật Mặt 48H</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix week years"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-3.jpg">
                                <div class="ep">1 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 9141
                                </div>
                                <h5>
                                    <a href="#">Con Nhót Mót Chồng</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix years month"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-4.jpg">
                                <div class="ep">1 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 9141
                                </div>
                                <h5>
                                    <a href="#">Fate/stay night: Heaven's Feel I. presage
                                        flower</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix day"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-5.jpg">
                                <div class="ep">5 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 9141
                                </div>
                                <h5>
                                    <a href="#">Hùng Long Phong Bá 2</a>
                                </h5>
                            </div>
                        </div>
                    </div>

                    <div class="product__sidebar__comment">
                        <div class="section-title">
                            <h5>BÌNH LUẬN MỚI</h5>
                        </div>
                        <div class="product__sidebar__comment__item">
                            <div class="product__sidebar__comment__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/sidebar/comment-1.jpg"
                                     alt="">
                            </div>
                            <div class="product__sidebar__comment__item__text">
                                <ul>
                                    <li>Hoạt động</li>
                                    <li>Phim</li>
                                </ul>
                                <h5>
                                    <a href="#">Phim hay quá mọi người ơi</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 19.141 Lượt xem</span>
                            </div>
                        </div>
                        <div class="product__sidebar__comment__item">
                            <div class="product__sidebar__comment__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/sidebar/comment-1.jpg"
                                     alt="">
                            </div>
                            <div class="product__sidebar__comment__item__text">
                                <ul>
                                    <li>Hoạt động</li>
                                    <li>Phim</li>
                                </ul>
                                <h5>
                                    <a href="#">Phim hay quá mọi người ơi</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 19.141 Lượt xem</span>
                            </div>
                        </div>
                        <div class="product__sidebar__comment__item">
                            <div class="product__sidebar__comment__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/sidebar/comment-1.jpg"
                                     alt="">
                            </div>
                            <div class="product__sidebar__comment__item__text">
                                <ul>
                                    <li>Hoạt động</li>
                                    <li>Phim</li>
                                </ul>
                                <h5>
                                    <a href="#">Phim hay quá mọi người ơi</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 19.141 Lượt xem</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Product Section End -->

<%@ include file="/views/common/footer.jsp" %>

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

</body>
</html>
