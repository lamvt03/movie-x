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
	let director = document.getElementsByName("director")[0].value;
	let actor = document.getElementsByName("actor")[0].value;
	let category = document.getElementsByName("category")[0].value;
	let heading = document.getElementsByName("heading")[0].value;
	let content = document.getElementsByName("content")[0].value;
	// let isActive = document.getElementsByName("isActive")[0];

	// if (!title || !href || !director || !actor || !category || !heading || !content) {
	// 	showSwalAlert('error', 'Thông tin phim không thể để trống!');
	// 	return false;
	// }
	// if (href.length > 50) {
	// 	showSwalAlert('error', 'Href không được lớn hơn 50 kí tự !');
	// 	return false;
	// }
	// if (title.length < 10) {
	// 	showSwalAlert('error', 'Tên phim không hợp lệ !');
	// 	return false;
	// }
	// if (actor.length < 10) {
	// 	showSwalAlert('error', 'Diễn viên không hợp lệ !');
	// 	return false;
	// }
	// if (isActive.value === "--Chọn--") {
	// 	showSwalAlert('error', 'Vui lòng chọn hiệu lực phim !');
	// 	return false;
	// }

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
			document.getElementById("ConfirmEditForm").submit();
			// Swal.fire(
			// 	'Thành công !',
			// 	'Chỉnh sửa video thành công !',
			// 	'success'
			// ).then(() => {
			// 	document.getElementById("ConfirmEditForm").submit();
			// });
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


//format giá tiền khi nhập vào
function formatPrice(input) {
	let rawValue = input.value.replace(/[^0-9]/g, '');

	let price = parseInt(rawValue);

	if (!isNaN(price)) {
		let formattedPrice = price.toLocaleString('vi-VN');
		input.value = formattedPrice;
	}
}