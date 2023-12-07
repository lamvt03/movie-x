<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam['website-name']} - Giới Thiệu</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<section class="normal-breadcrumb set-bg" style="height: 675px;"
         data-setbg="${pageContext.request.contextPath}/views/template/user/img/about/banner.png">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="normal__breadcrumb__text">
                    <h2 style="font-weight: 800;">Movie X</h2>
                    <p style="font-size: 36px; font-weight: 700;">Nền tảng xem phim trực tuyến</p>
                </div>
            </div>
        </div>
    </div>
</section>
<section class="mt-4">
    <div class="row">
        <div class="col-4">
            <img src="${pageContext.request.contextPath}/views/template/user/img/about/pic-1.png" alt=""/>
        </div>
        <div class="col-8">
            <h2 class="text-white font-weight-bolder mt-6 fs-10">Về chúng tôi</h2>
            <p class="text-white fs-4 mt-4">Movie X là một website xem phim trực tuyến miễn phí với kho phim khổng lồ, bao gồm nhiều thể loại phim khác nhau, từ phim bom tấn Hollywood đến phim điện ảnh Việt Nam, phim truyền hình, phim hoạt hình, phim tài liệu,...</p>
            <p class="text-white fs-4 mt-2">Với kho phim đa dạng, người dùng có thể tìm thấy bất kỳ bộ phim nào mình yêu thích, bất kể là thể loại gì. Từ những bộ phim hành động, bom tấn, viễn tưởng, kinh dị,... cho đến những bộ phim tình cảm, hài hước, lãng mạn,... đều có mặt trên Movie X.</p>
            <p class="text-white fs-4 mt-2">Ngoài ra, website cũng thường xuyên cập nhật các bộ phim mới, mang đến cho người dùng những trải nghiệm xem phim mới mẻ và thú vị.</p>
        </div>
    </div>
</section>

<%@ include file="/views/common/footer.jsp" %>
</body>
</html>
