<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${initParam['website-name']} | 505</title>
    <link rel="icon" href="${pageContext.request.contextPath}/views/error-pages/error.png" type="image/x-icon">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">

    <style type="text/css">
        .page_404 {
            padding: 40px 0;
            background: #fff;
            font-family: "Arvo", serif;
        }

        .page_404 img {
            width: 100%;
        }

        .four_zero_four_bg {
            background-image: url(https://cdn.dribbble.com/users/285475/screenshots/2083086/dribbble_1.gif);
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

        .contant_box_404 {
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
                        <h1 class="text-center fw-bold">500</h1>
                    </div>

                    <div class="contant_box_404">
                        <h3 class="h2 fw-bold">Opps... Hỏng Rồi !!!</h3>

                        <p class="fw-bold">Server Không Khả Dụng !</p>

                        <a href="${initParam['mvcPath']}/home" class="text-decoration-none fw-bold link_404">Quay Về Trang Chủ</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
