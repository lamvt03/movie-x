<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 5/21/2024
  Time: 5:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<nav class="d-flex justify-content-center">
    <ul class="product__pagination d-flex gap-2">
        <c:choose>
            <c:when test="${currentPage == 1}">
                <li class="disabled">
                    <a class="d-flex justify-content-center align-items-center" href="#"><i class="fa fa-angle-double-left"></i></a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a class="d-flex justify-content-center align-items-center" href="${initParam.mvcPath}${sessionScope.pathInfo}?page=${currentPage - 1}"><i class="fa fa-angle-double-left"></i></a>
                </li>
            </c:otherwise>
        </c:choose>

        <c:forEach varStatus="i" begin="1" end="${maxPage}">
            <li>
                <a href="${initParam.mvcPath}${sessionScope.pathInfo}?page=${i.index}"
                   class="current-page ${currentPage == i.index ? 'active' : ''}">${i.index}</a>
            </li>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage == maxPage}">
                <li class="disabled">
                    <a class="d-flex justify-content-center align-items-center" href="#"><i class="fa fa-angle-double-right"></i></a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a class="d-flex justify-content-center align-items-center" href="${initParam.mvcPath}${sessionScope.pathInfo}?page=${currentPage + 1}"><i class="fa fa-angle-double-right"></i></a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
