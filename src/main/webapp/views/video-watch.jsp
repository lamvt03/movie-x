<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/11/2023
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="video" scope="request" type="com.filmweb.dto.VideoDto"/>
<jsp:useBean id="comments" scope="request" type="java.util.List"/>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <title>${initParam['website-name']} - ${video.title}</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Breadcrumb Begin -->
<div id="breadcrumb" class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__links">
                    <a href="${initParam['mvcPath']}/home"><i class="fa fa-home"></i> Trang chủ</a> <a
                        href="${initParam['mvcPath']}/${video.categoryCode}">${video.category}</a> <a id="detail-btn"
                                                                                href="${initParam['mvcPath']}/video/detail?v=${video.href}">${video.title}</a> <span>XEM PHIM</span>
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
            <div class="col-lg-8">
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
                                        <img src="${pageContext.request.contextPath}/views/template/user/img/avt/avt-${comment.avtId}.jpg"
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
        </div>
    </div>
</section>
<!-- Anime Section End -->

<%@ include file="/views/common/footer.jsp" %>

<c:if test="${not empty sessionScope.buyBeforeWatch}">
    <script type="text/javascript">
        Swal.fire({
            title: 'Thông báo',
            text: "Bạn phải mua phim trước khi xem",
            icon: 'warning',
            confirmButtonColor: '#3085d6',
            confirmButtonText: 'Đồng ý'
        }).then((result) => {
            if (result.isConfirmed) {
                redirectToDetail();
            }
        });

        const redirectToDetail = () => {
            const detailBtn = document.getElementById("detail-btn");
            if (detailBtn) {
                detailBtn.click();
            }
        }
    </script>
    <c:remove var="buyBeforeWatch" scope="session"/>
</c:if>

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
                confirmButtonText: '<a style="color: white;" href="${initParam['mvcPath']}/login">Đăng Nhập</a>'
            })
        }
    </script>
</c:if>

<script type="text/javascript" src="${pageContext.request.contextPath}/views/template/user/js/showMoreComment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/template/user/js/postComment.js"> </script>

</body>
</html>
