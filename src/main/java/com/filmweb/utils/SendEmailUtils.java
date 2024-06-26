package com.filmweb.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class SendEmailUtils {

    @Inject
    @ConfigProperty(name = "host.url")
    private String hostUrl;

    public void sendEmail(
            String host,
            String port,
            final String username,
            final String password,
            String target,
            String subject,
            String content,
            String contentType
    ) throws AddressException, MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(username));
        InternetAddress[] toAddresses = {new InternetAddress(target)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(content, contentType);

        // sends the e-mail
        Transport.send(msg);
    }
    public String getRegisterMessageContent(String fullName, String token){
        return"<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
                + "    <meta charset=\"UTF-8\">\r\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                + "    <title>Web Film</title>\r\n"
                + "    <link rel=\"icon\" href=\"image/favicon.png\" type=\"icon/x-icon\">\r\n" + "</head>\r\n"
                + "\r\n" + "<body style=\" font-family: Raleway;\r\n" + "background-color: #d8dada;\r\n"
                + "font-size: 19px;\r\n" + "max-width: 800px;\r\n" + "margin: 0 auto;\r\n"
                + "padding: 3%;                       \">\r\n" + "\r\n" + "    <head>\r\n"
                + "        <div id=\"wrapper\" style=\"background-color: #f0f6fb;\">\r\n"
                + "            <header style=\"width: 98%;\">\r\n"
                + "                <div id=\"logo\" style=\"max-width: 80px;\r\n"
                + "                margin: 3% 0 3% 3%;\r\n" + "                float: left;\">\r\n"
                + "                </div>\r\n" + "                <div>\r\n" + "\r\n" + "            </header>\r\n"
                + "            <hr style=\"height: 1px;\r\n" + "            background-color: #303840;\r\n"
                + "            clear: both;\r\n" + "            width: 96%;\r\n"
                + "            margin: auto;\">\r\n" + "            <div id=\"banner\" style=\"margin: 3%;\">\r\n"
                + "                <div class=\"one-col\">\r\n"
                + "                    <h1 style=\"margin: 3%;\"> Xin Chào " + fullName + "  </h1>\r\n" + "\r\n"
                + "                    <p style=\"margin: 3%;\">\r\n" + "                        Thân gửi "
                + fullName + " ,<br><br>\r\n" + "\r\n"
                + "                        Cảm ơn bạn đã luôn tin tưởng và sử dụng ứng dụng của chúng tôi.<br><br>\r\n"
                + "\r\n" + "\r\n"
                + "                        Bạn có thể chỉnh sửa thông tin cá nhân của mình <a href=" + hostUrl + "/movie-x/profile\">tại đây</a> sau khi tài khoản được kích hoạt.\r\n"
                + "                       <br>\r\n" + "\r\n" + "<br>Trân trọng<br>\r\n" + "\r\n"
                + "                        Chi tiết xin liên hệ hotline để được tư vấn thêm.<br><br>\r\n"
                + "                        HotLine: 0886338217\r\n" + "                    </p>\r\n"
                + "                    <a href=" + hostUrl +"/movie-x/verify?token=" + token
                + " class=\"btn\" style=\"float: right;\r\n" + "                    margin: 0 2% 4% 0;\r\n"
                + "                    background-color: #303840;\r\n" + "                    color: #f6faff;\r\n"
                + "                    text-decoration: none;\r\n" + "                    font-weight: 800;\r\n"
                + "                    padding: 8px 12px;\r\n" + "                    border-radius: 8px;\r\n"
                + "                    letter-spacing: 2px;\">Kích Hoạt Tài Khoản</a>\r\n"
                + "                    <hr style=\"height: 1px;\r\n"
                + "                    background-color: #303840;\r\n" + "                    clear: both;\r\n"
                + "                    width: 96%;\r\n" + "                    margin: auto;\">\r\n"
                + "                    <footer>\r\n"
                + "                        <p id=\"contact\" style=\"text-align: center;\r\n"
                + "                        padding-bottom: 3%;\r\n"
                + "                        line-height: 16px;\r\n" + "                        font-size: 12px;\r\n"
                + "                        color: #303840;\">\r\n"
                + "                            Địa chỉ: 94 Nguyễn Phúc Chu, phường 15, quận Tân Bình, thành phố Hồ Chí Minh <br>\r\n"
                + "                            Điện thoại: 0886-338-217 <br>\r\n"
                + "                            Lịch hẹn: votruonglam2109@gmail.com <br>\r\n"
                + "                            Gmail: votruonglam2109@gmail.com\r\n"
                + "                        </p>\r\n" + "                    </footer>\r\n"
                + "                </div>\r\n" + "            </div>\r\n" + "    </head>\r\n" + "</body>\r\n"
                + "\r\n" + "</html>";
    }
    public String getForgotPasswordMessageContent(String fullName, String otp) {
        return "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n" + "<head>\r\n"
                + "    <meta charset=\"UTF-8\">\r\n"
                + "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                + "    <title>TRAVEL TOUR</title>\r\n"
                + "    <link rel=\"icon\" href=\"image/favicon.png\" type=\"icon/x-icon\">\r\n" + "</head>\r\n" + "\r\n"
                + "<body style=\" font-family: Raleway;\r\n" + "background-color: #d8dada;\r\n" + "font-size: 19px;\r\n"
                + "max-width: 800px;\r\n" + "margin: 0 auto;\r\n" + "padding: 3%;                       \">\r\n"
                + "\r\n" + "    <head>\r\n" + "        <div id=\"wrapper\" style=\"background-color: #f0f6fb;\">\r\n"
                + "            <header style=\"width: 98%;\">\r\n"
                + "                <div id=\"logo\" style=\"max-width: 80px;\r\n"
                + "                margin: 3% 0 3% 3%;\r\n" + "                float: left;\">\r\n"
                + "                </div>\r\n" + "                <div>\r\n" + "\r\n" + "            </header>\r\n"
                + "            <hr style=\"height: 1px;\r\n" + "            background-color: #303840;\r\n"
                + "            clear: both;\r\n" + "            width: 96%;\r\n" + "            margin: auto;\">\r\n"
                + "            <div id=\"banner\" style=\"margin: 3%;\">\r\n"
                + "                <div class=\"one-col\">\r\n"
                + "                    <h1 style=\"margin: 3%;\"> Xin Chào " + fullName + " !  </h1>\r\n" + "\r\n"
                + "                    <p style=\"margin: 3%;\">\r\n" + "                        Thân gửi " + fullName
                + " !,<br><br>\r\n" + "\r\n"
                + "                        Cảm ơn bạn đã luôn tin tưởng và sử dụng ứng dụng của chúng tôi!<br><br>\r\n"
                + "                        Vui lòng không chia sẽ mã này cho bất kì ai !<br><br>\r\n" + "\r\n" + "\r\n"
                + "                        Mã OTP khôi phục mật khẩu của bạn là: <Strong>" + otp + "</Strong> \r\n\r"
                + "                        Mã OTP sẽ hết hạn trong vòng 3 phút. \r\n"
                + "                       <br>\r\n" + "\r\n" + "<br>Trân trọng !<br>\r\n" + "\r\n"
                + "                        Chi tiết xin liên hệ hotline để được tư vấn thêm ! <br><br>\r\n"
                + "                        HotLine: 0886338217\r\n" + "                    </p>\r\n"
                + "                    <hr style=\"height: 1px;\r\n"
                + "                    background-color: #303840;\r\n" + "                    clear: both;\r\n"
                + "                    width: 96%;\r\n" + "                    margin: auto;\">\r\n"
                + "                    <footer>\r\n"
                + "                        <p id=\"contact\" style=\"text-align: center;\r\n"
                + "                        padding-bottom: 3%;\r\n" + "                        line-height: 16px;\r\n"
                + "                        font-size: 12px;\r\n" + "                        color: #303840;\">\r\n"
                + "                            Địa chỉ: 94 Nguyễn Phúc Chú, phường 15, quận Tân Bình, thành phố Hồ Chí Minh<br>\r\n"
                + "                            Điện thoại: 0886-338-217 <br>\r\n"
                + "                            Lịch hẹn: votruonglam2109@gmail.com <br>\r\n"
                + "                            Gmail: votruonglam2109@gmail.com\r\n"
                + "                        </p>\r\n" + "                    </footer>\r\n"
                + "                </div>\r\n" + "            </div>\r\n" + "    </head>\r\n" + "</body>\r\n" + "\r\n"
                + "</html>";
    }
}
