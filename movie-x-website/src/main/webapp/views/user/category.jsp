<jsp:useBean id="videos" scope="request" type="java.util.List"/>
<jsp:useBean id="category" scope="request" type="java.lang.String"/>

<jsp:useBean id="otherVideos" scope="request" type="java.util.List"/>
<jsp:useBean id="otherCategory" scope="request" type="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/11/2023
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - ${category}</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Breadcrumb Begin -->
<div class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__links">
                    <a href="${initParam.mvcPath}/home"><i class="fa fa-home"></i> Trang chủ</a>
                    <span>${category}</span>
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
                                    <h4>${category}</h4>
                                </div>
                            </div>
                            <div class="col-lg-4 col-md-4 col-sm-6 d-flex justify-content-end align-items-end">
                                <div class="product__page__filter">
                                    <p>Sắp xếp:</p>
                                    <select>
                                        <option value="">Mới nhất</option>
                                        <option value="">Cũ nhất</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <c:forEach items="${videos}" var="video">
                            <div class="col-lg-4 col-md-4 col-sm-6">
                                <div class="product__item">
                                    <a
                                            href="${initParam.mvcPath}/v/detail/${video.slug}">
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
                                            <a href="#">${video.title}</a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="col-12 col-lg-4 col-md-6 col-sm-8">
                <div class="product__sidebar">
                    <div class="product__sidebar__view">
                        <div class="section-title">
                            <h5 class="text-uppercase">MỘT SỐ ${otherCategory}</h5>
                        </div>
                        <div>
                            <c:forEach items="${otherVideos}" var="otherVideo">
                                <div class="product__sidebar__view__item set-bg mix day years"
                                     data-setbg="${otherVideo.poster}">
                                    <div class="overlay d-flex justify-content-center align-items-center">
                                        <a href="${initParam.mvcPath}/v/detail/${otherVideo.slug}" style="font-size: 20px;" class="text-white fw-bold">Xem ngay  <i class="fa-solid fa-play"></i></a>
                                    </div>
                                    <div class="ep">1 Tập</div>
                                    <div class="view">
                                        <i class="fa fa-eye"></i> ${otherVideo.views}
                                    </div>
                                    <h5>
                                        <a href="#">${otherVideo.title}</a>
                                    </h5>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Product Section End -->

<%@ include file="/views/user/common/footer.jsp" %>
</body>
</html>