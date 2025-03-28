<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 07-Oct-23
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${initParam.websiteName} - Trang chủ</title>
    <%@ include file="/views/user/common/head.jsp" %>
</head>
<body>
<%@ include file="/views/user/common/header.jsp" %>

<!-- Hero Section Begin -->
<section class="hero">
    <div class="container">
        <div class="hero__slider owl-carousel">

            <jsp:useBean id="trendingVideos" scope="request" type="java.util.List"/>
            <c:forEach items="${trendingVideos}" var="trendingVideo">
                <div class="hero__items set-bg" data-setbg="${trendingVideo.poster}">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="hero__text">
                                <div class="label">${trendingVideo.category}</div>
                                <h2>${trendingVideo.title}</h2>
                                <p>Thể loại: ${trendingVideo.category}</p>
                                <a href="${initParam.mvcPath}/v/detail/${trendingVideo.slug}"><span>Xem
													Ngay</span> <i class="fa-solid fa-angle-right"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<!-- Hero Section End -->

<!-- Product Section Begin -->
<section class="product spad">
    <div class="container">

        <div class="row">

            <div class="col-12 col-lg-8">
                <div class="trending__product">
                    <div class="row">
                        <div class="col-lg-8 col-md-8 col-sm-8">
                            <div class="section-title">
                                <h4>PHIM MỚI RA MẮT</h4>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-4 d-flex align-items-end justify-content-end">
                            <div class="btn__all">
                                <a href="${initParam.mvcPath}/all" class="primary-btn">Xem Tất Cả <i class="fa-solid fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </div>

                    <div class="row video-wrapper">

                        <jsp:useBean id="videos" scope="request" type="java.util.List"/>
                        <c:forEach items="${videos}" var="video">
                            <div class="col-lg-4 col-md-6 col-sm-12">
                                <div class="product__item overflow-hidden">
                                    <a
                                            href="${initParam.mvcPath}/v/detail/${video.slug}">
                                        <div class="product__item__pic set-bg"
                                             data-setbg="${video.poster}">
                                            <div class="ep">1 / 1</div>
                                            <div class="comment">
                                                <i class="fa-solid fa-heart"></i> ${video.likeQuantity}
                                            </div>
                                            <div class="view" style="margin-right: 50px">
                                                <i class="fa-solid fa-comment"></i> ${video.commentQuantity }
                                            </div>
                                            <div class="view">
                                                <i class="fa fa-eye"></i> ${video.views }
                                            </div>
                                        </div>
                                    </a>
                                    <div class="product__item__text">
                                        <ul class="d-flex align-items-center justify-content-between">
                                            <li>${video.category}</li>
                                            <p class="time-ago mb-0">${video.timeAgo}</p>
                                        </ul>

                                        <h5>
                                            <a href="#">${video.title}</a>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="row mt-2">
                        <button class="col-12 col-md-6 offset-md-3 col-lg-4 offset-lg-4 btn load-more-video-btn">
                            Xem Thêm
                        </button>

                    </div>
                </div>
            </div>

<%--            sidebar --%>
            <%@ include file="/views/user/common/sidebar.jsp" %>

        </div>
    </div>
</section>
<!-- Product Section End -->

<%@ include file="/views/user/common/footer.jsp" %>

<%--Load more video --%>
<script type="text/javascript">
    let page = 1;
    const loadMoreBtn = $('.load-more-video-btn');

    loadMoreBtn.on('click', function () {
        page++;
        performLoading()

        $.ajax(`/movie-x/api/video/list?page=\${page}`, {
            method: 'GET',
            dataType: 'json'
        }).done(function (data) {
            if (page >= data.maxPage) {
                loadMoreBtn.addClass('d-none');
            }

            const html = data.videos.map(video => `
                <div class="col-lg-4 col-md-6 col-sm-12">
                    <div class="product__item overflow-hidden">
                        <a href="/movie-x/v/detail/\${video.slug}">
                            <div style="background-image: url('\${video.poster}')" class="product__item__pic set-bg"
                                 data-setbg="\${video.poster}">
                                 <div class="ep">1 / 1</div>
                                <div class="comment">
                                    <i class="fa-solid fa-heart"></i> \${video.likeQuantity}
                                </div>
                                <div class="view" style="margin-right: 50px">
                                    <i class="fa-solid fa-comment"></i> \${video.commentQuantity}
                                </div>
                                <div class="view">
                                    <i class="fa fa-eye"></i> \${video.views}
                                </div>
                            </div>
                        </a>
                        <div class="product__item__text">
                            <ul class="d-flex align-items-center justify-content-between">
                                <li>\${video.category}</li>
                                <p class="time-ago mb-0">\${video.timeAgo}</p>
                            </ul>
                            <h5>
                                <a href="#">\${video.title}</a>
                            </h5>
                        </div>
                    </div>
                </div>
            `).join("\n");

            $('.video-wrapper').append(html);
            unPerformLoading()
        }).fail(function (error) {
            unPerformLoading()
            showSomethingWrongMessage();
        });
    });
</script>
</body>
</html>
