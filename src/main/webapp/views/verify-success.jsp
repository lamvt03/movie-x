<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/21/2023
  Time: 5:24 PM
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
    <title>${initParam['website-name']} - Xác Minh Thành Công</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Blog Details Section Begin -->
<section class="blog-details spad" style="min-height: 73vh">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-8">
                <div class="blog__details__content">
                    <div class="blog__details__text">
                        <p>
                            <i class="fa-solid fa-check"></i> Tài khoản ${sessionScope.email} đã được
                            kích hoạt thành công !
                        </p>
                        <a role="button" href="${initParam['mvcPath']}/login" class="site-btn">Đăng nhập</a>
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

<%@ include file="/views/common/footer.jsp" %>

<% session.removeAttribute("email"); %>
</body>
</html>
