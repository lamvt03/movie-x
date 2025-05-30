// top right alert
const showTopEndAlert = (icon, title) => {
    const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer);
            toast.addEventListener('mouseleave', Swal.resumeTimer);
        }
    });

    Toast.fire({
        icon: icon,
        title: title
    });
};

// center alert
const showCenterAlert = (icon, title, message) =>{
    Swal.fire(
        title,
        message,
        icon
    );
};

const showSomethingWrongMessage = () => showTopEndAlert('error', 'Đã có lỗi xảy ra, vui lòng liên hệ với quản trị viên');
