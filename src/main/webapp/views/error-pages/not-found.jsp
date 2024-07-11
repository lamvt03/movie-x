<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 3:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${initParam.websiteName} - Không tìm thấy trang</title>
    <link rel="icon" href="${pageContext.request.contextPath}/views/error-pages/error.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/common/css/bootstrap.min.css" />

    <style>
        .page_404 {
            padding: 40px 0;
            background: #ffffff;
            font-family: "Arvo", serif;
        }

        .page_404 img {
            width: 100%;
        }

        .four_zero_four_bg {
            background-image: url(${pageContext.request.contextPath}/views/error-pages/error.gif);
            height: 400px;
            background-position: center;
        }

        .four_zero_four_bg h1 {
            font-size: 80px;
        }

        .four_zero_four_bg h3 {
            font-size: 80px;
        }

        .link_404 {
            color: #fff !important;
            padding: 10px 20px;
            background: #39ac31;
            margin: 20px 0;
            display: inline-block;
        }

        .content_box_404 {
            font-family: Arial, serif;
            margin-top: -50px;
        }
    </style>
</head>
<body>
<section class="page_404">
    <div class="container ">
        <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-12 col-sm-offset-1 text-center">
                    <div class="four_zero_four_bg">
                        <h1 class="text-center fw-bold">404</h1>
                    </div>

                    <div class="content_box_404">
                        <h3 class="h2 fw-bold">Có vẻ như bạn đã nhầm lẫn</h3>

                        <p class="fw-bold">Trang bạn đang tìm kiếm không khả dụng!</p>

                        <a href="${initParam.mvcPath}/home" class="text-decoration-none fw-bold link_404">Quay Về Trang Chủ</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script type="text/javascript" src="${pageContext.request.contextPath}/views/common/js/bootstrap.bundle.min.js"></script>
</body>
</html>
