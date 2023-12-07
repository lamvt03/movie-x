<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/3/2023
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <title>${initParam['website-name']} - Tất cả phim </title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Breadcrumb Begin -->
<div class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__links">
                    <a href="${initParam['mvcPath']}/home"><i class="fa fa-home"></i> Trang chủ</a>
                    <span>Thể loại</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Breadcrumb End -->

<!-- Product Section Begin -->
<section class="product-page spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="product__page__content">
                    <div class="product__page__title">
                        <div class="row">
                            <div class="col-lg-8 col-md-8 col-sm-6">
                                <div class="section-title">
                                    <h4>Tất cả phim</h4>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6">
                                <div class="product__page__filter">
                                    <p>Sắp xếp:</p>
                                    <select>
                                        <option value="">A-Z</option>
                                        <option value="">1-10</option>
                                        <option value="">10-50</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <jsp:useBean id="videos" scope="request" type="java.util.List"/>
                        <c:forEach items="${videos}" var="video">
                            <div class="col-lg-4 col-md-6 col-sm-6">
                                <div class="product__item">
                                    <a
                                            href="${initParam['mvcPath']}/video/detail?v=${video.href}">
                                        <div class="product__item__pic set-bg"
                                             data-setbg="${video.poster}">
                                            <div class="comment">
                                                <i class="fa-solid fa-heart"></i> ${video.likeQuantity}
                                            </div>
                                            <div class="view" style="margin-right: 50px">
                                                <i class="fa fa-eye"></i> ${video.commentQuantity }
                                            </div>
                                            <div class="view">
                                                <i class="fa fa-eye"></i> ${video.views }
                                            </div>
                                        </div>
                                    </a>
                                    <div class="product__item__text">
                                        <ul class="d-flex align-items-center justify-content-between">
                                            <li>${video.category}</li>
                                            <p class="time-ago mb-0">${video.timeAgo}</p>
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
                        <a href="${initParam['mvcPath']}/category?page=${currentPage - 1}"><i
                                class="fa fa-angle-double-left"></i></a>
                    </c:if>
                    <c:forEach varStatus="i" begin="1" end="${maxPage}">
                        <a href="${initParam['mvcPath']}/category?page=${i.index}" style="margin-left: 10px"
                           class="current-page ${currentPage == i.index ? 'active' : ''}">${i.index}</a>
                    </c:forEach>

                    <c:if test="${currentPage == maxPage}">
                        <a href="#"><i class="fa fa-angle-double-right"></i></a>
                    </c:if>
                    <c:if test="${currentPage < maxPage}">
                        <a href="${initParam['mvcPath']}/category?page=${currentPage + 1}"><i
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
                                <div class="ep">41 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 20
                                </div>
                                <h5>
                                    <a href="#">Tân Ỷ Thiên Đồ Long Ký </a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix month week"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-2.jpg">
                                <div class="ep">42 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 15
                                </div>
                                <h5>
                                    <a href="#">Điệp Viên Huyền Thoại</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix week years"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-3.jpg">
                                <div class="ep">57 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 100
                                </div>
                                <h5>
                                    <a href="#">Tây Du Ký</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix years month"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-4.jpg">
                                <div class="ep">44 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 20
                                </div>
                                <h5>
                                    <a href="#">Sự Thật Phanh Phui</a>
                                </h5>
                            </div>
                            <div class="product__sidebar__view__item set-bg mix day"
                                 data-setbg="${pageContext.request.contextPath}/views/template/user/img/sidebar/tv-5.jpg">
                                <div class="ep">3 Tập</div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> 56
                                </div>
                                <h5>
                                    <a href="#">Diệp Vấn</a>
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
                                    <li>Phim khoa học viễn tưởng</li>
                                </ul>
                                <h5>
                                    <a href="#">very good</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 300 Lượt xem</span>
                            </div>
                        </div>
                        <div class="product__sidebar__comment__item">
                            <div class="product__sidebar__comment__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/sidebar/comment-2.jpg"
                                     alt="">
                            </div>
                            <div class="product__sidebar__comment__item__text">
                                <ul>
                                    <li>Phim hành động Mỹ</li>
                                </ul>
                                <h5>
                                    <a href="#">Phim hay quá mọi người ơi</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 100 Lượt xem</span>
                            </div>
                        </div>
                        <div class="product__sidebar__comment__item">
                            <div class="product__sidebar__comment__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/sidebar/comment-3.jpg"
                                     alt="">
                            </div>
                            <div class="product__sidebar__comment__item__text">
                                <ul>
                                    <li>Phim hiếm hiệp Trung Quốc</li>
                                </ul>
                                <h5>
                                    <a href="#">Phim đỉnh quá</a>
                                </h5>
                                <span><i class="fa fa-eye"></i> 130 Lượt xem</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Product Section End -->

<%@ include file="/views/common/footer.jsp"%>

</body>
</html>
