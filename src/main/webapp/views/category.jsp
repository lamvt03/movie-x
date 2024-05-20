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
                            <div class="col-lg-4 col-md-4 col-sm-6 d-flex justify-content-end align-items-end">
                                <div class="product__page__filter">
                                    <p>Sắp xếp:</p>
                                    <label>
                                        <select>
                                            <option value="">A-Z</option>
                                            <option value="">1-10</option>
                                            <option value="">10-50</option>
                                        </select>
                                    </label>
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

            <%@ include file="/views/common/sidebar.jsp"%>
        </div>
    </div>
</section>
<!-- Product Section End -->

<%@ include file="/views/common/footer.jsp"%>

</body>
</html>
