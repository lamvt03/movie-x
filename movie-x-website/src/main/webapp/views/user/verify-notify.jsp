<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 6/15/2024
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<jsp:useBean id="now" class="java.util.Date"/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Gửi mail xác minh thành công</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Blog Details Section Begin -->
<section class="blog-details spad" style="min-height: 73vh">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-8">
                <div class="blog__details__content">
                    <div class="blog__details__text">
                        <p>
                            <i class="fa-solid fa-circle-exclamation"></i> Gửi mail xác minh thành công. Bạn vui lòng kiểm tra email để xác minh tài khoản. Nếu không nhận được email, vui lòng kiểm tra hòm thư rác.
                        </p>
                        <p>Bạn có thể đóng tab này</p>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="blog__details__title">
                    <h6>
                        <fmt:formatDate value="${now}" pattern="EEE, HH:mm:ss, dd-MM-yyyy"/>
                    </h6>
                    <div class="blog__details__social">
                        <a href="#" class="facebook"><i class="fa-brands fa-facebook-f"></i>
                            Facebook</a>
                        <a href="#" class="pinterest"><i class="fa-brands fa-pinterest"></i> Pinterest</a>
                        <a href="#" class="linkedin"><i class="fa-brands fa-linkedin"></i>
                            Linkedin</a>
                        <a href="#" class="twitter"><i class="fa-brands fa-twitter"></i> Twitter</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<!-- Blog Details Section End -->

<%@ include file="/views/user/common/footer.jsp" %>
</body>
</html>

