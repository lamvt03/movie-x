<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/11/2023
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="video" scope="request" type="com.moviex.dto.VideoDto"/>
<jsp:useBean id="comments" scope="request" type="java.util.List"/>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - ${video.title}</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Breadcrumb Begin -->
<div id="breadcrumb" class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__links">
                    <a href="${initParam.mvcPath}/home"><i class="fa fa-home"></i> Trang chủ</a> <a
                        href="${initParam.mvcPath}/${video.categorySlug}">${video.category}</a> <a id="detail-btn"
                                                                                href="${initParam.mvcPath}/v/detail/${video.slug}">${video.title}</a> <span>XEM PHIM</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Breadcrumb End -->

<!-- Anime Section Begin -->
<section class="anime-details spad">
    <div class="container">
        <div class="row">
            <div id="video" class="col-lg-12">
                <div id="player" data-plyr-provider="youtube" data-plyr-embed-id="${video.href}">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12 col-lg-8 col-md-6">
                <div class="anime__details__review">
                    <div class="section-title">
                        <c:if test="${comments.size() > 0}">
                            <h5>Bình luận</h5>
                        </c:if>
                    </div>

                    <div>
                        <div class="review-container">
                            <c:forEach items="${comments}" var="comment">
                                <div class="anime__review__item">
                                    <div class="anime__review__item__pic">
                                        <img src="${comment.userImage}"
                                             alt="avt"/>
                                    </div>
                                    <div class="anime__review__item__text">
                                        <h6>
                                                ${comment.createdBy} - <span>${comment.timeAgo}</span>
                                        </h6>
                                        <p>${comment.content}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <c:choose>
                            <c:when test="${lastPage > 1}">
                                <div class="float-end">
                                    <span class="showMoreBtn">Hiển thị thêm bình luận <i class="fa-solid fa-angle-down"></i></span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="float-end">
                                    <span class="d-none showMoreBtn">Hiển thị thêm bình luận <i class="fa-solid fa-angle-down"></i></span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="anime__details__form">
                    <div class="section-title">
                        <h5>Để lại đánh giá</h5>
                    </div>
                    <div>
                        <textarea class="cmtInp" placeholder="Nội dung..." name="content" required></textarea>
                        <input class="href" name="href" type="hidden" value="${video.href}">
                        <c:choose>
                            <c:when test="${not empty sessionScope.currentUser}">
                                <button id="sendCmtBtn" type="button">
                                    <i class="fa fa-location-arrow"></i> Gửi Bình Luận
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button id="guestBtn" type="button">
                                    <i class="fa fa-location-arrow"></i> Gửi Bình Luận
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-4 col-md-6">
                <div class="product__sidebar">
                    <div class="product__sidebar__view">
                        <div class="section-title">
                            <h5>Có thể bạn sẽ thích</h5>
                        </div>
                        <div class="filter__gallery">
                            <jsp:useBean id="relatedVideos" scope="request" type="java.util.List"/>
                            <c:forEach items="${relatedVideos}" var="relatedVideo">
                                <div class="product__sidebar__view__item set-bg"
                                     data-setbg="${relatedVideo.poster}">
                                    <div class="overlay d-flex justify-content-center align-items-center">
                                        <a href="${initParam.mvcPath}/v/detail/${relatedVideo.slug}" style="font-size: 20px;" class="text-white fw-bold">Xem ngay  <i class="fa-solid fa-play"></i></a>
                                    </div>
                                    <div class="ep">1 Tập</div>
                                    <div class="view">
                                        <i class="fa fa-eye"></i> ${relatedVideo.views}
                                    </div>
                                    <h5>
                                        <a href="#">${relatedVideo.title}</a>
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
<!-- Anime Section End -->

<%@ include file="/views/user/common/footer.jsp" %>

<c:if test="${empty sessionScope.currentUser}">
    <script type="text/javascript">
        const guestBtn = document.querySelector('#guestBtn');
        guestBtn.onclick = () => {
            Swal.fire({
                title: 'Thông báo',
                text: "Vui lòng đăng nhập trước khi bình luận",
                icon: 'info',
                showCloseButton: true,
                showCancelButton: true,
                focusCancel: false,
                cancelButtonColor: '#e74c3c',
                cancelButtonText: 'Huỷ',
                confirmButtonColor: '#27ae60',
                confirmButtonText: '<a style="color: white;" href="${initParam.mvcPath}/login">Đăng Nhập</a>'
            })
        }
    </script>
</c:if>

<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/showMoreComment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/assets/js/postComment.js"> </script>

</body>
</html>
