// toast alert
function showSwalAlert(icon, title) {
	const Toast = Swal.mixin({
		toast: true,
		position: 'top-end',
		showConfirmButton: false,
		timer: 3000,
		timerProgressBar: true,
		didOpen: (toast) => {
			toast.addEventListener('mouseenter', Swal.stopTimer)
			toast.addEventListener('mouseleave', Swal.resumeTimer)
		}
	});

	Toast.fire({
		icon: icon,
		title: title
	});
}

// center alert
function showCenterAlert(icon, title, message) {
	Swal.fire(
		title,
		message,
		icon
	)
}

// bắt lỗi login
function validateLogin() {
	const username = document.getElementsByName("username")[0].value;
	const password = document.getElementsByName("password")[0].value;

	if (!username) {
		showSwalAlert('error', 'Vui lòng nhập tên đăng nhập !');
		return false;
	}
	if (!password) {
		showSwalAlert('error', 'Vui lòng nhập mật khẩu !');
		return false;
	}
	if (password.length < 5) {
		showSwalAlert('error', 'Mật khẩu phải có ít nhất 6 ký tự !');
		return false;
	}

	return true;
}

// bắt lỗi add phim
function validateNewFilm() {
	let title = document.getElementsByName("title")[0].value;
	let href = document.getElementsByName("href")[0].value;
	let daodien = document.getElementsByName("daodien")[0].value;
	let dienvien = document.getElementsByName("dienvien")[0].value;
	let category = document.getElementsByName("category")[0].value;
	let mota = document.getElementsByName("mota")[0].value;
	let noted = document.getElementsByName("noted")[0].value;
	let isActive = document.getElementsByName("isActive")[0];

	if (title == "" && href == "" && daodien == "" && dienvien == "" && category == "" && mota == "" && noted == "") {
		showSwalAlert('error', 'Thông tin phim không thể để trống!');
		return false;
	}
	if (href.length > 50) {
		showSwalAlert('error', 'Href không được lớn hơn 50 kí tự !');
		return false;
	}
	if (title.length < 10) {
		showSwalAlert('error', 'Tên phim không hợp lệ !');
		return false;
	}
	if (dienvien.length < 10) {
		showSwalAlert('error', 'Diễn viên không hợp lệ !');
		return false;
	}
	if (isActive.value === "--Chọn--") {
		showSwalAlert('error', 'Vui lòng chọn hiệu lực phim !');
		return false;
	}

	return true;

}

// cofirm edit video
function editVideo() {
	Swal.fire({
		title: 'Cảnh Báo !',
		text: "Bạn có chắc chắn chỉnh sửa video không ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Đồng ý !'
	}).then((result) => {
		if (result.isConfirmed) {
			document.getElementById("confirmEdit").value = "true";
			Swal.fire(
				'Thành công !',
				'Chỉnh sửa video thành công !',
				'success'
			).then(() => {
				document.getElementById("ConfirmEditForm").submit();
			});
		}
	});

	return false;
}

// cofirm xoá video
function deleteVideo(href) {
	Swal.fire({
		title: 'Cảnh Báo !',
		text: "Bạn có chắc chắn ngưng công chiếu phim không ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Đồng ý !'
	}).then((result) => {
		if (result.isConfirmed) {
			document.getElementById("confirmDelete").value = "true";
			Swal.fire(
				'Thành công !',
				'Đổi trạng thái phim thành công !',
				'success'
			).then(() => {
				document.getElementById("videoHref").value = href;
				document.getElementById("videoForm").submit();
			});
		}
	});

	return false;
}

// cofirm edit user
function ConfirmEditUser() {
	Swal.fire({
		title: 'Cảnh Báo !',
		text: "Bạn có chắc chắn chỉnh sửa người dùng không ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Đồng ý !'
	}).then((result) => {
		if (result.isConfirmed) {
			document.getElementById("EditUser").value = "true";
			Swal.fire(
				'Thành công !',
				'Chỉnh sửa người dùng thành công !',
				'success'
			).then(() => {
				document.getElementById("ConfirmEditUser").submit();
			});
		}
	});

	return false;
}

// cofirm retore video công chiếu
function CofirmVideoToViews(href) {
	Swal.fire({
		title: 'Cảnh Báo !',
		text: "Bạn có chắc chắn muốn công chiếu phim trở lại không ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Đồng ý !'
	}).then((result) => {
		if (result.isConfirmed) {
			document.getElementById("confirmDelete").value = "true";
			Swal.fire(
				'Thành công !',
				'Đổi trạng thái phim thành công !',
				'success'
			).then(() => {
				document.getElementById("RestoreVideo").value = href;
				document.getElementById("RestoreFormDisabled").submit();
			});
		}
	});

	return false;
}

//format giá tiền khi nhập vào
function formatPrice(input) {
	let rawValue = input.value.replace(/[^0-9]/g, '');

	let price = parseInt(rawValue);

	if (!isNaN(price)) {
		let formattedPrice = price.toLocaleString('vi-VN');
		input.value = formattedPrice;
	}
}