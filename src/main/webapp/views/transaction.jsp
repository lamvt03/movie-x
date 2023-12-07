<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 11:00 AM
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
    <title>${initParam['website-name']} - Lịch Sử Giao Dịch</title>
    <%@ include file="/views/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/common/header.jsp" %>

<!-- Blog Details Section Begin -->
<section class="login spad container" style="min-height: 73vh">
    <div class="text-white text-center">
        <h4 class="font-weight-bold">Lịch Sử Giao Dịch</h4>
        <br>
    </div>
    <div class="blog__details__title">
        <h6>
            <fmt:formatDate value="${now}" pattern="EEE, HH:mm:ss, dd-MM-yyyy"/>
        </h6>
    </div>
    <div class="rounded-lg">
        <div class="row bg-white p-2 m-1" style="border-radius: 6px">
            <div class="col-lg-12">
                <div class="blog__details__content">
                    <div class="table-responsive">
                        <table class="table bg-white">
                            <thead>
                            <tr style="font-size: 14px">
                                <th scope="col">#</th>
                                <th scope="col">Mã giao dịch</th>
                                <th scope="col">Video</th>
                                <th scope="col">Hình thức thanh toán</th>
                                <th scope="col">Ngày</th>
                                <th scope="col">Giờ</th>
                                <th scope="col">Giá tiền</th>
                                <th scope="col">Trạng thái</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${orders}" var="order" varStatus="loop">
                                <tr style="font-size: 14px">
                                    <td scope="row">${loop.index + 1}</td>
                                    <td width="130px">${order.vnp_TxnRef}</td>
                                    <td width="130px"><a
                                            class="text-info fs-6 text-decoration-none font-weight-bold"
                                            href="${initParam['mvcPath']}/video/watch?v=${order.video.href}">Xem tại
                                        đây</a></td>
                                    <td>NGÂN HÀNG ${order.vnp_BankCode}</td>
                                    <td><fmt:formatDate value="${order.vnp_PayDate}"
                                                        pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${order.vnp_PayDate}"
                                                        pattern="HH:mm:ss"/></td>
                                    <td><c:set var="amount" value="${order.vnp_Amount}"/> <c:set
                                            var="locale" value="vi_VN"/> <fmt:setLocale
                                            value="${locale}"/> <fmt:formatNumber
                                            value="${amount}" type="currency" currencyCode="VND"/></td>
                                    <td width="140px"><c:choose>
                                        <c:when test="${order.vnp_ResponseCode == '00'}">
														<span class="text-success font-weight-bold">Thành
															công</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-danger font-weight-bold">Thất bại</span>
                                        </c:otherwise>
                                    </c:choose></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Blog Details Section End -->

<%@ include file="/views/common/footer.jsp" %>
</body>
</html>
