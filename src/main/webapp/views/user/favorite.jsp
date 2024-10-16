<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 1:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="videos" scope="request" type="java.util.List"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Phim Yêu Thích</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Blog Section Begin -->
<section class="blog spad" style="padding-top: 70px">
    <div class="container">
        <div class="text-white text-center">
            <h4 class="font-weight-bold">Phim yêu thích</h4>
            <br>
        </div>
        <div class="blog__details__title">
            <h6>
                <fmt:formatDate value="${now}" pattern="EEE, HH:mm:ss, dd-MM-yyyy" />
            </h6>
        </div>

        <div class="row">

            <c:forEach items="${videos}" var="video">
                <div class="col-lg-3 col-md-6 col-sm-6">
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
                            <ul>
                                <li>${video.category}</li>
                            </ul>
                            <h5>
                                <a href="#">${video.title }</a>
                            </h5>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<!-- Blog Section End -->

<%@ include file="/views/user/common/footer.jsp" %>
</body>
</html>
