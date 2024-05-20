

// validate form login



// validate register form
function validateRegisterForm() {
    const emailRegex = /\b[\w.%-]+@[-.\w]+\.[A-Za-z]{2,4}\b/;
    const phoneRegex = /^(0|\+84)\d{9,10}$/;
    const nameRegex = /^[\p{L}'][\p{L}'\s-]{0,}$/u;

    const email = document.getElementsByName("email")[0].value;
    const phone = document.getElementsByName("phone")[0].value;
    const fullname = document.getElementsByName("fullName")[0].value;
    const password = document.getElementsByName("password")[0].value;
    const passConfirm = document.getElementsByName("passwordConfirm")[0].value;

    if (!email) {
        showTopEndAlert('error', 'Vui lòng nhập địa chỉ Email');
        return false;
    }
    if (!emailRegex.test(email)) {
        showTopEndAlert('error', 'Địa chỉ Email không đúng định dạng');
        return false;
    }
    if (!fullname) {
        showTopEndAlert('error', 'Vui lòng nhập họ và tên');
        return false;
    }
    if (fullname.length < 12) {
        showTopEndAlert('error', 'Họ tên không hợp lệ');
        return false;
    }
    if (!nameRegex.test(fullname)) {
        showTopEndAlert('error', 'Họ và tên không đúng định dạng Việt Nam');
        return false;
    }
    if (!phone) {
        showTopEndAlert('error', 'Vui lòng nhập số điện thoại');
        return false;
    }
    if (!phoneRegex.test(phone)) {
        showTopEndAlert('error', 'Số điện thoại không đúng định dạng Việt Nam');
        return false;
    }
    if (!password) {
        showTopEndAlert('error', 'Vui lòng nhập mật khẩu');
        return false;
    }
    if (password.length < 6) {
        showTopEndAlert('error', 'Mật khẩu phải có ít nhất 6 ký tự');
        return false;
    }
    if (!passConfirm) {
        showTopEndAlert('error', 'Vui lòng nhập lại mật khẩu');
        return false;
    }
    if (password !== passConfirm) {
        showTopEndAlert('error', 'Nhập lại mật khẩu không khớp');
        return false;
    }

    document.getElementById("submitRegister").disabled = true;

    Swal.fire({
        title: 'Xác nhận',
        text: "Bạn có chắc chắn đăng ký tài khoản hay không ?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý !'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("register-form-submit").submit();
        } else {
            document.getElementById("submitRegister").disabled = false;
        }
    });

    return false;
}

// validate newPassword
function validateNewPassword() {
    var password = document.getElementsByName("password")[0].value;
    var passConfirm = document.getElementsByName("confirm-password")[0].value;

    if (password == "") {
        showTopEndAlert('error', 'Vui lòng nhập mật khẩu !');
        return false;
    }
    if (password.length < 6) {
        showTopEndAlert('error', 'Mật khẩu phải có ít nhất 6 ký tự !');
        return false;
    }
    if (passConfirm == "") {
        showTopEndAlert('error', 'Vui lòng nhập lại mật khẩu !');
        return false;
    }
    if (password != passConfirm) {
        showTopEndAlert('error', 'Nhập lại mật khẩu không khớp !');
        return false;
    }

    return true;
}

// validate change password
function validateChangePass() {
    const oldPass = document.getElementsByName("oldPass")[0].value;
    const newPass = document.getElementsByName("newPass")[0].value;
    const confirmPass = document.getElementsByName("cofirmPass")[0].value;

    if (!oldPass) {
        showTopEndAlert('error', 'Vui lòng nhập mật khẩu cũ !');
        return false;
    }
    if (!newPass) {
        showTopEndAlert('error', 'Vui lòng nhập mật khẩu mới !');
        return false;
    }
    if (newPass.length < 6) {
        showTopEndAlert('error', 'Mật khẩu mới phải có ít nhất 6 ký tự !');
        return false;
    }
    if (!confirmPass) {
        showTopEndAlert('error', 'Vui lòng nhập lại mật khẩu !');
        return false;
    }
    if (confirmPass !== newPass) {
        showTopEndAlert('error', 'Mật khẩu không đồng nhất !');
        return false;
    }

    Swal.fire({
        title: 'Cảnh Báo !',
        text: "Bạn có chắc chắn thay đổi mật khẩu không ?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý !'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("confirmationField").value = "true";
            document.getElementById("form").submit();
            }
        });
    return false;
}

// validate edit profile
function checkEditProfile() {
    const fullName = document.getElementsByName("fullname")[0].value;
    const phone = document.getElementsByName("phone")[0].value;

    if (!fullName) {
        showTopEndAlert('error', 'Vui lòng nhập họ và tên !');
        return false;
    }
    if (!phone) {
        showTopEndAlert('error', 'Vui lòng nhập số điện thoại !');
        return false;
    }

    Swal.fire({
        title: 'Cảnh Báo !',
        text: "Bạn có chắc chắn thay đổi thông tin không ?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đồng ý !'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("confirmationField").value = "true";
            Swal.fire(
                'Thành công !',
                'Thay đổi thông tin thành công !',
                'success'
            ).then(() => {
                document.getElementById("form").submit();
            });
        }
    });
    return false;
}