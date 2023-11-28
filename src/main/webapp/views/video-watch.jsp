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
    <title>${video.title}</title>
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
                        href="categories">Thể loại</a> <a id="detail-btn"
                        href="${initParam['mvcPath']}/video/detail?v=${video.href}">Thông
                    Tin Phim</a> <span>${video.title}</span>
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
            <div class="col-lg-12">
                <div class="anime__video__player">
                    <iframe id="player" width="100%" height="600"
                            src="https://www.youtube.com/embed/${video.href}" frameborder="0"
                            allowfullscreen></iframe>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-8">
                <div class="anime__details__review">
                    <div class="section-title">
                        <h5>Bình Luận</h5>
                    </div>

                    <c:forEach items="${comments}" var="comment">
                        <div class="anime__review__item">
                            <div class="anime__review__item__pic">
                                <img src="${pageContext.request.contextPath}/views/template/user/img/default-avt.jpg" alt="avt"/>
                            </div>
                            <div class="anime__review__item__text">
<%--                                <c:forEach items="${user}" var="users">--%>
                                    <h6>
                                            ${comment.createdBy} - <span>${comment.timeAgo}</span>
                                    </h6>
<%--                                </c:forEach>--%>
                                <p>${comment.content}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="anime__details__form">
                    <c:if test="${not empty sessionScope.currentUser}">
<%--                        <div class="section-title">--%>
<%--                            <h5>Để lại đánh giá</h5>--%>
<%--                        </div>--%>

                        <form action="${initParam['mvcPath']}/video/comment" method="post">
                            <textarea placeholder="Nội dung..." name="content" required></textarea>
                            <input name="href" type="hidden" value="${video.href}">

                            <button type="submit">
                                <i class="fa fa-location-arrow"></i> Gửi Bình Luận
                            </button>

                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Anime Section End -->

<%@ include file="/views/common/footer.jsp" %>
<%
    Boolean buyBeforeWatch = (Boolean) session.getAttribute("buyBeforeWatch");
    if(buyBeforeWatch != null){
%>
<script type="text/javascript">
    Swal.fire({
        title: 'Thông báo',
        text: "Bạn phải mua phim trước khi xem",
        icon: 'warning',
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'Đồng ý'
    }).then((result) => {
        if(result.isConfirmed){
            document.getElementById("detail-btn").click();
            <%--console.log("http://localhost:8080/app/video/detail?v=" + ${video.href})--%>
            <%--window.location.href = "http://localhost:8080/app/video/detail?v=" + ${video.href};--%>
        }
    })
</script>
<%
        session.removeAttribute("buyBeforeWatch");
    }
%>

</body>
</html>
