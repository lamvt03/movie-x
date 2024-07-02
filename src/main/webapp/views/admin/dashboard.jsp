<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Administrator - Dashboard</title>
    <%@ include file="/views/admin/common/head.jsp" %>
</head>
<body>
<!--  Body Wrapper -->
<section class="page-wrapper" id="main-wrapper" data-layout="vertical"
         data-navbarbg="skin6" data-sidebartype="full"
         data-sidebar-position="fixed" data-header-position="fixed">

    <!-- Sidebar Start -->

    <%@ include file="/views/admin/common/assied.jsp" %>

    <!--  Sidebar End -->

    <!--  Main wrapper -->
    <div class="body-wrapper">

        <!--  Header Start -->
        <%@ include file="/views/admin/common/header.jsp" %>
        <!--  Header End -->

        <div class="container-fluid">
            <!--  Row 1 -->
            <div class="row">
                <div class="col-lg-8 d-flex align-items-strech">
                    <div class="card w-100">
                        <div class="card-body">
                            <div
                                    class="d-sm-flex d-block align-items-center justify-content-between mb-9">
                                <div class="mb-3 mb-sm-0">
                                    <h5 class="card-title fw-semibold">Biểu Đồ Thống Kê</h5>
                                </div>
                                <div>
                                    <select class="form-select">
                                        <option value="1">March 2023</option>
                                        <option value="2">April 2023</option>
                                        <option value="3">May 2023</option>
                                        <option value="4">June 2023</option>
                                    </select>
                                </div>
                            </div>
                            <div id="chart"></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="row">
                        <div class="col-lg-12">
                            <!-- Yearly Breakup -->
                            <div class="card overflow-hidden">
                                <div class="card-body p-4">
                                    <h5 class="card-title mb-9 fw-semibold">Doanh Thu Hàng Năm</h5>
                                    <div class="row align-items-center">
                                        <div class="col-8">
                                            <h4 class="fw-semibold mb-3">
                                                <c:set var="amount" value="${totalPrice}"/>
                                                <c:set var="locale" value="vi_VN"/>
                                                <fmt:setLocale value="${locale}"/>
                                                <fmt:formatNumber value="${amount}" type="currency"
                                                                  currencyCode="VND"/>
                                            </h4>
                                            <div class="d-flex align-items-center mb-3">
													<span
                                                            class="me-1 rounded-circle bg-light-success round-20 d-flex align-items-center justify-content-center">
														<i class="ti ti-arrow-up-left text-success"></i>
													</span>
                                                <p class="text-dark me-1 fs-3 mb-0">+100%</p>
                                                <p class="fs-3 mb-0">So với năm trước</p>
                                            </div>
                                            <div class="d-flex align-items-center">
                                                <div class="me-4">
														<span
                                                                class="round-8 bg-primary rounded-circle me-2 d-inline-block"></span>
                                                    <span class="fs-2">2023</span>
                                                </div>
                                                <div>
														<span
                                                                class="round-8 bg-light-primary rounded-circle me-2 d-inline-block"></span>
                                                    <span class="fs-2">2022</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div class="d-flex justify-content-center">
                                                <div id="breakup"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <!-- Monthly Earnings -->
                            <div class="card">
                                <div class="card-body">
                                    <div class="row alig n-items-start">
                                        <div class="col-8">
                                            <h5 class="card-title mb-9 fw-semibold">Doanh Thu Hàng
                                                Tháng</h5>
                                            <h4 class="fw-semibold mb-3">
                                                <c:set var="amount" value="${totalPrice}"/>
                                                <c:set var="locale" value="vi_VN"/>
                                                <fmt:setLocale value="${locale}"/>
                                                <fmt:formatNumber value="${amount}" type="currency"
                                                                  currencyCode="VND"/>
                                            </h4>
                                            <div class="d-flex align-items-center pb-1">
													<span
                                                            class="me-2 rounded-circle bg-light-danger round-20 d-flex align-items-center justify-content-center">
														<i class="ti ti-arrow-up-left text-success"></i>
													</span>
                                                <p class="text-dark me-1 fs-3 mb-0">+100%</p>
                                                <p class="fs-3 mb-0">So với tháng trước</p>
                                            </div>
                                        </div>
                                        <div class="col-4">
                                            <div class="d-flex justify-content-end">
                                                <div
                                                        class="text-white bg-secondary rounded-circle p-6 d-flex align-items-center justify-content-center">
                                                    <i class="ti ti-currency-dollar fs-6"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="earning"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-4 d-flex align-items-stretch">
                    <div class="card w-100">
                        <div class="card-body p-2">
                            <div class="mb-4">
                                <h5 class="card-title fw-semibold">Giao dịch gần đây</h5>
                            </div>
                            <ul class="timeline-widget mb-0 position-relative mb-n5">
                                <jsp:useBean id="orders" scope="request" type="java.util.List"/>
                                <c:forEach items="${orders}" var="order">
                                    <li class="timeline-item d-flex position-relative overflow-hidden">
                                        <div class="timeline-time text-dark flex-shrink-0 text-end">
                                            <fmt:formatDate value="${order.vnp_PayDate}"
                                                            pattern="dd-MM-yyyy | HH:mm:ss"/>
                                        </div>
                                        <div class="timeline-badge-wrap d-flex flex-column align-items-center">
                                            <span class="timeline-badge border-2 border border-success flex-shrink-0 my-8"></span>
                                        </div>
                                        <div class="timeline-desc fs-3 text-dark mt-n1">${order.vnp_OrderInfo}</div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-lg-8 d-flex align-items-stretch">
                    <div class="card w-100">
                        <div class="card-body p-2">
                            <h5 class="card-title fw-semibold mb-4">Chi tiết giao dịch</h5>
                            <div class="table-responsive">
                                <table class="table text-nowrap mb-0 align-middle">
                                    <thead class="text-dark fs-4">
                                    <tr>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">#</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Mã giao dịch</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Họ tên</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Ngân hàng</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Giá tiền</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Trạng thái</h6>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${orders}" var="order" varStatus="loop">
                                        <tr>
                                            <td class="border-bottom-0">
                                                <h6 class="fw-semibold mb-0">${loop.index + 1}</h6>
                                            </td>
                                            <td class="border-bottom-0">
                                                <h6 class="fw-semibold mb-1">${order.vnp_TxnRef}</h6>
                                            </td>
                                            <td class="border-bottom-0">
                                                <p class="mb-0 fw-normal">${order.user.fullName}</p>
                                            </td>
                                            <td class="border-bottom-0">
                                                <div class="d-flex align-items-center gap-2">
                                                    <span class="badge bg-primary rounded-3 fw-semibold">${order.vnp_BankCode}</span>
                                                </div>
                                            </td>
                                            <td class="border-bottom-0">
                                                <h6 class="fw-semibold mb-0">
                                                    <c:set var="amount" value="${order.vnp_Amount}"/>
                                                    <c:set var="locale" value="vi_VN"/>
                                                    <fmt:setLocale value="${locale}"/>
                                                    <fmt:formatNumber value="${amount}" type="currency"
                                                                      currencyCode="VND"/>
                                                </h6>
                                            </td>
                                            <td class="border-bottom-0">
                                                <h6 class="fw-semibold mb-0">
                                                    <c:choose>
                                                        <c:when test="${order.vnp_ResponseCode == '00'}">
																	<span class="text-success font-weight-bold">Thành
																		công</span>
                                                        </c:when>
                                                        <c:otherwise>
																	<span class="text-danger font-weight-bold">Thất
																		bại</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </h6>
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
        </div>
    </div>
</section>

<%@ include file="/views/admin/common/footer.jsp" %>
</body>
</html>
