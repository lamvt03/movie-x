<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="topVideos" scope="request" type="java.util.List"/>
<jsp:useBean id="newestComments" scope="request" type="java.util.List"/>

<%--top section --%>
<div class="col-lg-4 col-md-6 col-sm-8">
    <div class="product__sidebar">
        <div class="product__sidebar__view">
            <div class="section-title">
                <h5>TOP XEM HÀNG ĐẦU</h5>
            </div>
            <ul class="filter__controls">
                <li class="active" data-filter=".years">Năm</li>
                <li data-filter=".month">Tháng</li>
                <li data-filter=".week">Tuần</li>
            </ul>
            <div class="filter__gallery">
                <c:forEach items="${topVideos}" var="topVideo">
                    <div class="product__sidebar__view__item set-bg mix day years"
                         data-setbg="${topVideo.poster}">
                        <div class="overlay d-flex justify-content-center align-items-center">
                            <a href="${initParam['mvcPath']}/video/watch?v=${topVideo.href}" style="font-size: 20px;" class="text-white fw-bold">Xem ngay  <i class="fa-solid fa-play"></i></a>
                        </div>
                        <div class="ep">1 Tập</div>
                        <div class="view">
                            <i class="fa fa-eye"></i> ${topVideo.views}
                        </div>
                        <h5>
                            <a href="#">${topVideo.title}</a>
                        </h5>
                    </div>
                </c:forEach>
            </div>
        </div>

<%--        comment section --%>
        <div class="product__sidebar__comment">
            <div class="section-title">
                <h5>BÌNH LUẬN MỚI</h5>
            </div>
            <c:forEach items="${newestComments}" var="newestComment">
                <div class="product__sidebar__comment__item">
                    <div class="product__sidebar__comment__item__pic">
                        <img class="rounded-top img-fluid" src="${newestComment.userImage}"
                             alt="">
                    </div>
                    <div class="product__sidebar__comment__item__text">
                        <h5 class="text-white mb-0 mt-3">
                            ${newestComment.createdBy}
                        </h5>
                        <p class="text-light-indigo">
                            ${newestComment.content}
                        </p>
                        <span class="rounded bg-light-gray text-dark-light p-1 mt-3">${newestComment.timeAgo}</span>
                    </div>
                </div>
            </c:forEach>

        </div>
    </div>
</div>