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
<%@ page import="java.net.URLEncoder" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <title>${initParam['website-name']} - ${video.title}</title>
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
                    <a href="${initParam['mvcPath']}/home"><i class="fa fa-home"></i> Trang chủ</a> <a
                        href="${initParam['mvcPath']}/${video.categoryCode}">${video.category}</a> <span>${video.title}</span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Breadcrumb End -->

<section class="anime-details spad">
    <div class="container">
        <div class="anime__details__content">
            <div class="row">
                <div class="col-lg-3">
                    <div
                            class="anime__details__pic set-bg"
                            data-setbg="${video.poster}">
                        <img class="img-fluid" src="${video.poster}" alt="">
                        <div class="comment">
                            <i class="fa fa-comments"></i> ${video.commentQuantity}
                        </div>
                        <div class="view">
                            <i class="fa fa-eye"></i> ${video.views}
                        </div>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="anime__details__text">
                        <div class="row">
                            <div class="col-12 col-md-9 col-lg-9">
                                <div class="anime__details__title">
                                    <h3>${video.title}</h3>
                                    <span>Quốc gia: Việt Nam</span>
                                </div>
                            </div>

                            <c:if test="${not empty sessionScope.currentUser}">

                                <div class="col-12 col-md-3 col-lg-3">
                                    <form id="ratingForm" action="${initParam['mvcPath']}/video/rating" method="post">
                                        <div class="anime__details__rating">
                                            <div class="rating-start">
                                                <input type="radio" name="rating" id="rating-5" value="5"
                                                    ${checkedAttribute5}> <label for="rating-5"></label>
                                                <input type="radio" name="rating" id="rating-4" value="4"
                                                    ${checkedAttribute4}> <label for="rating-4"></label>
                                                <input type="radio" name="rating" id="rating-3" value="3"
                                                    ${checkedAttribute3}> <label for="rating-3"></label>
                                                <input type="radio" name="rating" id="rating-2" value="2"
                                                    ${checkedAttribute2}> <label for="rating-2"></label>
                                                <input type="radio" name="rating" id="rating-1" value="1"
                                                    ${checkedAttribute1}> <label for="rating-1"></label>
                                                <input class="href" name="href" type="hidden"
                                                       value="${video.href}">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </c:if>
                        </div>

                        <div class="anime__details__widget">
                            <div class="row">
                                <div class="col-12 col-lg-12 col-md-12">
                                    <ul>
                                        <li><span>Diễn viên:</span> ${video.actor}</li>
                                        <li><span>Đạo diễn:</span> ${video.director}</li>
                                        <li><span>Thể loại:</span> ${video.category}</li>
                                        <li><span>Mô tả:</span> ${video.description}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="anime__details__btn">

                            <c:if test="${not empty sessionScope.currentUser}">
                                <button id="likeButton" class="follow-btn"
                                        style="border: none; white-space: nowrap; width: 120px;
                            box-sizing: border-box;">
                                    <c:choose>

                                        <c:when test="${flagLikeButton == true}">
                                            Bỏ Thích
                                        </c:when>
                                        <c:otherwise>
                                            Thích
                                        </c:otherwise>
                                    </c:choose>
                                </button>

                                <button type="button" class="follow-btn border-0"
                                        data-toggle="modal" data-target="#exampleModal">
                                    <i class="fa-regular fa-share-from-square"></i> Chia sẻ
                                </button>

                            </c:if>

                            <c:choose>
                                <c:when test="${video.price == 0}">
                                    <a
                                            href="${initParam['mvcPath']}/video/watch?v=${video.href}"
                                            class="watch-btn"><span>Xem ngay</span> <i
                                            class="fa fa-angle-right"></i></a>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${order.vnp_ResponseCode == '00'}">
                                            <a
                                                    href="${initParam['mvcPath']}/video/watch?v=${video.href}"
                                                    class="watch-btn"><span>Xem ngay</span> <i
                                                    class="fa fa-angle-right"></i></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${not empty sessionScope.currentUser}">
                                                <fmt:setLocale value="vi_VN"/>
                                                <c:set var="formattedPrice" value="${video.price}"/>
                                                <fmt:formatNumber var="formattedPrice"
                                                                  value="${formattedPrice}" type="currency"
                                                                  currencyCode="VND"/>
                                                <a onclick="clickConfirmPayment()" style="cursor: pointer;"
                                                   class="watch-btn"><span>${formattedPrice}</span> <i
                                                        class="fa fa-angle-right"></i></a>
                                            </c:if>

                                            <c:if test="${empty sessionScope.currentUser}">
                                                <fmt:setLocale value="vi_VN"/>
                                                <c:set var="formattedPrice" value="${video.price}"/>
                                                <fmt:formatNumber var="formattedPrice"
                                                                  value="${formattedPrice}" type="currency"
                                                                  currencyCode="VND"/>
                                                <a class="watch-btn" id="clickBeforeLogin"
                                                   style="cursor: pointer;"> <span>${formattedPrice}</span>
                                                    <i class="fa fa-angle-right"></i>
                                                </a>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8 col-md-8">
                <div class="anime__details__review">
                    <div class="section-title">
                        <c:if test="${comments.size() > 0}">
                            <h5 class="mb-6">Bình luận</h5>
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
                        <c:if test="${lastPage > 1}">
                            <div class="float-end">
                                <span class="showMoreBtn">Hiển thị thêm bình luận <i class="fa-solid fa-angle-down"></i></span>
                            </div>
                        </c:if>
                    </div>

                </div>

            </div>

        </div>
    </div>
</section>
<!-- Anime Section End -->

<div class="modal fade" id="exampleModal" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title font-weight-bold">Chia Sẻ Video</h5>
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form onsubmit="return disabledButtonOnclickSendEmail()"
                  action="${initParam['mvcPath']}/video/share" method="post">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="email" class="form-label">Đến</label> <input
                            type="text" class="form-control" name="email" id="email"
                            placeholder="Vui lòng nhập Email">
                    </div>
                    <div class="mb-3">
                        <div class="mb-3">
                            <label for="note" class="form-label">Ghi chú</label>
                            <textarea class="form-control" name="noted" id="note"
                                      placeholder="Vui lòng nhập ghi chú" rows="3"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary rounded-0"
                            data-dismiss="modal">Thoát
                    </button>
                    <button type="submit" id="submit"
                            class="btn btn-success rounded-0 ps-3 px-3">Gửi
                    </button>
                </div>
                <input class="href" name="href" type="hidden"
                       value="${video.href}"/>
            </form>
        </div>
    </div>
</div>

<%@ include file="/views/common/footer.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/views/template/user/js/showMoreComment.js"></script>
<script type="text/javascript">
    const likeButton = document.querySelector('#likeButton');
    likeButton.onclick = () => {
        loadingContainer.classList.remove("invisible");
        const href = document.querySelector('.href').value;
        fetch('/movie-x/api/video/like?v=' + href, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => response.json())
            .then((data) => {
                loadingContainer.classList.add("invisible");
                if (data.isLiked) {
                    likeButton.innerText = 'Bỏ thích';
                } else {
                    likeButton.innerText = 'Thích';
                }
            })
    }
</script>

<%
    Boolean sendMailSuccess = (Boolean) session.getAttribute("sendMailSuccess");
    if (sendMailSuccess != null) {
        if (sendMailSuccess) {
%>
<script>
    showSwalAlert('success', 'Chia sẻ video thành công !');
</script>
<%
        }
        session.removeAttribute("sendMailSuccess");
    }
%>

<script type="text/javascript">
    /* validate form send email */
    function disabledButtonOnclickSendEmail() {
        const email = document.getElementsByName("email")[0].value;
        const emailRegex = /\b[\w.%-]+@[-.\w]+\.[A-Za-z]{2,4}\b/;

        if (!email) {
            showSwalAlert('error', 'Vui lòng nhập địa chỉ email !');
            return false;
        }
        if (!emailRegex.test(email)) {
            showSwalAlert('error', 'Địa chỉ Email không đúng định dạng !');
            return false;
        }

        document.getElementById("submit").disabled = true;
    }

    /* hỏi khi thanh toán */
    function clickConfirmPayment() {
        Swal.fire({
            title: 'Xác nhận',
            text: "Bạn có chắc chắn muốn mua phim không ?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "/movie-x/payment/vnpay?href=${video.href}";
            }
        })
    }

    <c:if test="${empty sessionScope.currentUser}">
    /* thông báo đăng nhập khi thanh toán */
    const clickPayment = document.getElementById('clickBeforeLogin');

    clickPayment.onclick = () => {
        Swal.fire({
            title: 'Thanh toán',
            text: "Bạn phải đăng nhập trước khi thanh toán",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý'
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = "/movie-x/login";
            }
        })
    }
    </c:if>

    <c:if test="${not empty sessionScope.paySuccess}">
        <c:choose>
            <c:when test="${sessionScope.paySuccess}">
                Swal.fire({
                    title: 'Chúc mừng',
                    text: "Bạn đã mua phim thành công!",
                    icon: 'success',
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            </c:when>
            <c:otherwise>
                Swal.fire({
                    title: 'Thông báo',
                    text: "Đã có lỗi xảy ra!",
                    icon: 'error',
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: 'OK'
                });
            </c:otherwise>
        </c:choose>

        <c:remove var="paySuccess" scope="session" />
    </c:if>
</script>
</body>
</html>
