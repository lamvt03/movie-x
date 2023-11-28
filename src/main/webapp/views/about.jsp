<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 11/28/2023
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/views/common/taglib.jsp"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Giới Thiệu</title>
    <%@ include file="/views/common/head.jsp"%>
</head>
<body>
<%@ include file="/views/common/header.jsp"%>

<!-- Blog Details Section Begin -->
<section class="blog-details spad">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-8">
                <div class="blog__details__title">
                    <h6>
                        Cập nhật lần cuối vào lúc <span>00:00:00 07/04/2023</span>
                    </h6>
                    <h2>Chúng Tôi Có Những Gì</h2>
                    <div class="blog__details__social">
                        <a href="#" class="facebook"><i class="fa fa-facebook-square"></i>
                            Facebook</a> <a href="#" class="pinterest"><i
                            class="fa fa-pinterest"></i> Pinterest</a> <a href="#"
                                                                          class="linkedin"><i class="fa fa-linkedin-square"></i>
                        Linkedin</a> <a href="#" class="twitter"><i
                            class="fa fa-twitter-square"></i> Twitter</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-12">
                <div class="blog__details__pic">
                    <img src="views/template/user/img/blog/details/blog-details-pic.jpg" alt="">
                </div>
            </div>
            <div class="col-lg-8">
                <div class="blog__details__content">
                    <div class="blog__details__text">
                        <p>Trang web chúng tôi là trang web xem phim. chúng tôi luôn
                            luôn cung cấp những thước phim mới nhất và hay nhất cho các bạn
                            xem, khi chúng tôi thấy được những thước phim đã được công khai
                            thì chúng tôi sẽ khai thác về trang web để phục vụ tận tình cho
                            các bạn và cũng như người xem, cảm ơn tất cả các bạn đã ủng hộ
                            website!</p>
                    </div>
                    <div class="blog__details__item__text">
                        <h4>Những thước phim hài hước:</h4>
                        <img src="views/template/user/img/blog/details/bd-item-1.jpg" alt="">
                        <p>Phim hài hước, pha lẫn một chút tình cảm để lấy đi nước
                            mắt người xem ở những đoạn cuối phim rất đáng để xem, các bạn có
                            thể tham khảo. Mình có để tên phim trong mỗi poster.</p>
                    </div>
                    <div class="blog__details__item__text">
                        <h4>Những thước phim tình cảm:</h4>
                        <img src="views/template/user/img/blog/details/bd-item-2.jpg" alt="">
                        <p>Trang web chúng tôi là trang web xem phim. chúng tôi luôn
                            luôn cung cấp những thước phim mới nhất và hay nhất cho các bạn
                            xem, khi chúng tôi thấy được những thước phim đã được công khai
                            thì chúng tôi sẽ khai thác về trang web để phục vụ tận tình cho
                            các bạn và cũng như người xem, cảm ơn tất cả các bạn đã ủng hộ
                            website!</p>
                    </div>
                    <div class="blog__details__item__text">
                        <h4>Series về quê ăn tết:</h4>
                        <img src="views/template/user/img/blog/details/bd-item-3.jpg" alt="">
                        <p>Trang web chúng tôi là trang web xem phim. chúng tôi luôn
                            luôn cung cấp những thước phim mới nhất và hay nhất cho các bạn
                            xem, khi chúng tôi thấy được những thước phim đã được công khai
                            thì chúng tôi sẽ khai thác về trang web để phục vụ tận tình cho
                            các bạn và cũng như người xem, cảm ơn tất cả các bạn đã ủng hộ
                            website!</p>
                    </div>
                    <div class="blog__details__tags">
                        <a href="#">Hài hước</a> <a href="#">Tình cảm</a> <a href="#">Hành
                        động</a>
                    </div>
                    <div class="blog__details__btns">
                        <div class="row">
                            <div class="col-6 col-md-6 col-lg-6">
                                <div class="blog__details__btns__item">
                                    <h5>
                                        <a href="#"><span class="arrow_left"></span> Trước đó</a>
                                    </h5>
                                </div>
                            </div>
                            <div class="col-6 col-md-6 col-lg-6">
                                <div class="blog__details__btns__item next__btn">
                                    <h5>
                                        <a href="#">Tiếp theo <span class="arrow_right"></span></a>
                                    </h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="blog__details__comment">
                        <h4>3 Bình luận</h4>
                        <div class="blog__details__comment__item">
                            <div class="blog__details__comment__item__pic">
                                <img src="views/template/user/img/blog/details/comment-1.png" alt="">
                            </div>
                            <div class="blog__details__comment__item__text">
                                <span>05 - 08, 2022</span>
                                <h5>Bá Hữu</h5>
                                <p>Trang web rất hay, tiện ích lại dễ sử dụng. Tôi rất
                                    thích !</p>
                                <a href="#">Thích</a> <a href="#">Trả lời</a>
                            </div>
                        </div>
                        <div
                                class="blog__details__comment__item blog__details__comment__item--reply">
                            <div class="blog__details__comment__item__pic">
                                <img src="views/template/user/img/blog/details/comment-2.png" alt="">
                            </div>
                            <div class="blog__details__comment__item__text">
                                <span>06 - 08, 2022</span>
                                <h5>Văn Hậu</h5>
                                <p>Trang web rất hay, tiện ích lại dễ sử dụng. Tôi rất
                                    thích !</p>
                                <a href="#">Thích</a> <a href="#">Trả lời</a>
                            </div>
                        </div>
                        <div class="blog__details__comment__item">
                            <div class="blog__details__comment__item__pic">
                                <img src="views/template/user/img/blog/details/comment-3.png" alt="">
                            </div>
                            <div class="blog__details__comment__item__text">
                                <span>12 - 06, 2023</span>
                                <h5>Quang Nhựt</h5>
                                <p>Trang web rất hay, tiện ích lại dễ sử dụng. Tôi rất
                                    thích !</p>
                                <a href="#">Thích</a> <a href="#">Trả lời</a>
                            </div>
                        </div>
                    </div>
                    <div class="blog__details__form">
                        <h4>Để lại bình luận</h4>
                        <form action="#">
                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <input type="text" placeholder="Họ và tên">
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6">
                                    <input type="text" placeholder="Địa chỉ Email">
                                </div>
                                <div class="col-lg-12">
                                    <textarea placeholder="Nội dung"></textarea>
                                    <button type="submit" class="site-btn">Gửi Nội Dung</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Blog Details Section End -->

<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
