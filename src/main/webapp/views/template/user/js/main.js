/*  ---------------------------------------------------
	Theme Name: Anime
	Description: Anime video tamplate
	Author: Colorib
	Author URI: https://colorib.com/
	Version: 1.0
	Created: Colorib
---------------------------------------------------------  */

'use strict';

(function($) {

	/*------------------
		Preloader
	--------------------*/
	$(window).on('load', function() {
		$(".loader").fadeOut();
		$("#preloader").delay(200).fadeOut("slow");

		/*------------------
			FIlter
		--------------------*/
		$('.filter__controls li').on('click', function() {
			$('.filter__controls li').removeClass('active');
			$(this).addClass('active');
		});
		if ($('.filter__gallery').length > 0) {
			var containerEl = document.querySelector('.filter__gallery');
			var mixer = mixitup(containerEl);
		}
	});

	/*------------------
		Background Set
	--------------------*/
	$('.set-bg').each(function() {
		var bg = $(this).data('setbg');
		$(this).css('background-image', 'url(' + bg + ')');
	});

	// Search model
	$('.search-switch').on('click', function() {
		$('.search-model').fadeIn(400);
	});

	$('.search-close-switch').on('click', function() {
		$('.search-model').fadeOut(400, function() {
			$('#search-input').val('');
		});
	});

	/*------------------
		Navigation
	--------------------*/
	$(".mobile-menu").slicknav({
		prependTo: '#mobile-menu-wrap',
		allowParentLinks: true
	});

	/*------------------
		Hero Slider
	--------------------*/
	var hero_s = $(".hero__slider");
	hero_s.owlCarousel({
		loop: true,
		margin: 0,
		items: 1,
		dots: true,
		nav: true,
		navText: ["<i class='fa-solid fa-angle-left' style='transform: rotate(-45deg)'></i>", "<i class='fa-solid fa-angle-right' style='transform: rotate(-45deg)'></i>"],
		animateOut: 'fadeOut',
		animateIn: 'fadeIn',
		smartSpeed: 1200,
		autoHeight: false,
		autoplay: true,
		mouseDrag: false
	});

	/*------------------
		Video Player
	--------------------*/
	const player = new Plyr('#player', {
		seekTime: 15
	});

	/*------------------
		Niceselect
	--------------------*/
	$('select').niceSelect();

	/*------------------
		Scroll To Top
	--------------------*/
	$("#scrollToTopButton").click(function() {
		$("html, body").animate({ scrollTop: 0 }, "slow");
		return false;
	});

})(jQuery);

// ajax video

//Ajax để gửi dữ liệu đánh giá lên servlet
function sendRatingToServlet(ratingValue) {
	var hrefValue = document.getElementById('videoIdHidden').value;

	$.ajax({
		url: 'rating', // Địa chỉ URL của servlet xử lý đánh giá
		type: 'POST',
		data: {
			rating: ratingValue,
			href: hrefValue
		}, // Dữ liệu đánh giá và href gửi lên
		success: function() {
			console.log('Đánh giá đã được gửi thành công.');
			// Cập nhật giao diện hoặc thực hiện các hành động khác nếu cần
		},
		error: function(xhr, status, error) {
			console.error('Lỗi gửi đánh giá:', error);
		}
	});
}
// Gán sự kiện cho từng input radio
var ratingInputs = document.querySelectorAll('input[type="radio"][name="rating"]');
ratingInputs.forEach(function(input) {
	input.addEventListener('change', function() {
		// Lấy giá trị đánh giá khi có sự thay đổi
		var ratingValue = this.value;
		// Gửi dữ liệu đánh giá lên servlet
		sendRatingToServlet(ratingValue);
	});
});