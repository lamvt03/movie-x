<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 10/17/2024
  Time: 5:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${initParam.websiteName} - Điều khoản sử dụng</title>
    <%@ include file="/views/user/common/head.jsp" %>
    <style>
        h1, h2, p, ul, li {
            color: white;
        }
    </style>
</head>
<body>
<section class="hero">
    <div class="container">
        <section>
            <h1>Chính sách bảo mật MOVIE X</h1>
                <p>
                    Tại MOVIE X, chúng tôi cam kết bảo vệ quyền riêng tư và thông tin cá nhân của bạn. Chính sách bảo
                    mật này giải thích cách chúng tôi thu thập, sử dụng, và bảo vệ thông tin cá nhân của bạn khi sử dụng
                    dịch vụ của chúng tôi.
                </p>

                <h2>1. Thông tin chúng tôi thu thập</h2>
                <p>
                    Khi bạn sử dụng dịch vụ của MOVIE X, chúng tôi có thể thu thập các loại thông tin cá nhân sau:
                </p>
                <ul>
                    <li>Chi tiết cá nhân (tên, địa chỉ email, số điện thoại, địa chỉ)</li>
                    <li>Chi tiết thanh toán (thẻ tín dụng, phương thức thanh toán khác)</li>
                    <li>Thông tin tài khoản/hồ sơ MOVIE X</li>
                    <li>Thông tin sử dụng dịch vụ (lịch sử xem phim, tìm kiếm)</li>
                    <li>Thông tin quảng cáo (tương tác với quảng cáo, ưu đãi)</li>
                    <li>Thông tin thiết bị và mạng (địa chỉ IP, loại thiết bị, thông tin trình duyệt)</li>
                    <li>Thông tin liên lạc (yêu cầu hỗ trợ, phản hồi)</li>
                </ul>

                <h2>2. Cách chúng tôi sử dụng thông tin</h2>
                <p>Chúng tôi sử dụng thông tin cá nhân của bạn cho các mục đích sau:</p>
                <ul>
                    <li>Quản lý tài khoản và cung cấp dịch vụ MOVIE X</li>
                    <li>Xử lý thanh toán và giao dịch</li>
                    <li>Cải thiện dịch vụ và trải nghiệm người dùng</li>
                    <li>Gửi thông báo và ưu đãi liên quan đến dịch vụ MOVIE X</li>
                    <li>Phân tích dữ liệu và thực hiện nghiên cứu thị trường</li>
                    <li>Bảo vệ an ninh và ngăn ngừa gian lận</li>
                </ul>

                <h2>3. Cách chúng tôi bảo vệ thông tin cá nhân của bạn</h2>
                <p>
                    Chúng tôi áp dụng các biện pháp bảo mật hợp lý để bảo vệ thông tin cá nhân của bạn khỏi việc truy
                    cập, sử dụng hoặc tiết lộ trái phép. Các biện pháp bảo mật bao gồm mã hóa dữ liệu, kiểm soát truy
                    cập và lưu trữ an toàn thông tin.
                </p>

                <h2>4. Chia sẻ thông tin cá nhân của bạn</h2>
                <p>
                    Chúng tôi chia sẻ thông tin cá nhân của bạn với các bên sau:
                </p>
                <h3>Với các nhà cung cấp dịch vụ</h3>
                <p>
                    Chúng tôi chia sẻ thông tin cá nhân với các nhà cung cấp dịch vụ để giúp cung cấp, quản lý và cải
                    thiện các dịch vụ của chúng tôi. Các nhà cung cấp dịch vụ chỉ được phép sử dụng thông tin cá nhân
                    của bạn để cung cấp các dịch vụ mà chúng tôi yêu cầu và không được sử dụng hoặc tiết lộ thông tin
                    này cho bất kỳ mục đích nào khác.
                </p>

                <h3>Với đối tác</h3>
                <p>
                    Chúng tôi có thể chia sẻ thông tin cá nhân của bạn với các đối tác để kích hoạt và phân phối nội
                    dung, quảng cáo, tiếp thị và các dịch vụ liên quan. Các đối tác phải tuân thủ các yêu cầu bảo mật và
                    chỉ được phép sử dụng thông tin này cho mục đích đã được xác định.
                </p>

                <h2>5. Quyền của bạn đối với thông tin cá nhân</h2>
                <p>
                    Bạn có quyền truy cập, sửa đổi hoặc xóa thông tin cá nhân của mình bất cứ lúc nào bằng cách đăng
                    nhập vào tài khoản MOVIE X hoặc liên hệ với chúng tôi qua thông tin liên hệ dưới đây. Bạn cũng có
                    quyền yêu cầu ngừng chia sẻ thông tin cá nhân của mình cho mục đích tiếp thị.
                </p>

                <h2>6. Liên hệ</h2>
                <p>
                    Nếu bạn có bất kỳ câu hỏi nào về chính sách bảo mật này hoặc cách chúng tôi xử lý thông tin cá nhân
                    của bạn, vui lòng liên hệ với chúng tôi qua email tại <a href="mailto:support@moviex.com">support@moviex.com</a>.
                </p>

                <footer class="m-auto mt-5">
                    <p class="text-center">Bản quyền © MOVIE X. Mọi quyền được bảo lưu.</p>
                </footer>
    </div>

</section>


</body>
</html>

