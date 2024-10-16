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
<jsp:useBean id="paymentTransactions" scope="request" type="java.util.List"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Lịch Sử Giao Dịch</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Blog Details Section Begin -->
<section class="login spad container mt-5" style="min-height: 73vh">
    <div class="text-white text-center">
        <h4 class="font-weight-bold">Lịch Sử Nạp Tiền</h4>
        <br>
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
                                <th scope="col">Phương thức</th>
                                <th scope="col">Loại thẻ</th>
                                <th scope="col">Số tiền</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Thời điểm</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${paymentTransactions}" var="paymentTransaction" varStatus="loop">
                                <tr style="font-size: 14px">
                                    <td>${loop.index + 1}</td>
                                    <td>${paymentTransaction.referenceTransactionNumber}</td>
                                    <td>
                                        <span class="badge bg-primary rounded-3 fw-semibold">${paymentTransaction.provider}</span>
                                    </td>
                                    <td>
                                        <span class="badge bg-primary rounded-3 fw-semibold">${paymentTransaction.cardType}</span>
                                    </td>
                                    <td>
                                        ${paymentTransaction.formattedPaymentAmount}
                                    </td>
                                    <td>
                                        <span class="fw-semibold mb-0 badge bg-${paymentTransaction.statusCode}">
                                                ${paymentTransaction.statusMessage}
                                        </span>
                                    </td>
                                    <td>
                                        <span>
                                                ${paymentTransaction.createdAt}
                                        </span>
                                    </td>
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

<%@ include file="/views/user/common/footer.jsp" %>
</body>
</html>
