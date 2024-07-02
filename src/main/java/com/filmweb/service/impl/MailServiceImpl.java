package com.filmweb.service.impl;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.UserVerifiedEmail;
import com.filmweb.service.MailService;
import com.filmweb.utils.RandomUtils;
import com.filmweb.utils.MailUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@ApplicationScoped
public class MailServiceImpl implements MailService {
    @Inject
    private MailUtils mailUtils;

    @Inject
    private RandomUtils randomUtils;

    @Inject
    private UserVerifiedEmailDao verifiedEmailDao;

    @Inject
    @ConfigProperty(name="host.url")
    private String hostUrl;

    @Override
    public void sendRegisterEmail(UserDto recipient) throws MessagingException, UnsupportedEncodingException {
        UserVerifiedEmail verifiedEmail = new UserVerifiedEmail();
        String token = randomUtils.randomToken(AppConstant.REGISTER_TOKEN_LENGTH);
        verifiedEmail.setToken(token);
        verifiedEmail.setExpiredAt(LocalDateTime.now().plusMinutes(AppConstant.REGISTER_TOKEN_MINUTES));
        verifiedEmail.setUserId(recipient.getId());
        verifiedEmailDao.create(verifiedEmail);

        String subject = "Kích hoạt tài khoản của bạn trên Movie X";
        String content = this.createRegistrationMail(recipient.getFullName(), token);
        String contentType = "text/html; charset=utf-8";
        mailUtils.sendEmail(recipient.getEmail(), subject, content, contentType);
    }

    @Override
    public void sendForgotEmail(UserDto recipient, String otp) throws MessagingException, UnsupportedEncodingException {
        String subject = "Yêu cầu đổi mật khẩu tài khoản Movie X";
        String content = this.createForgotMail(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        mailUtils.sendEmail(recipient.getEmail(), subject, content, contentType);
    }

    private String createRegistrationMail(String fullName, String token) {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"
                + "<title>Movie X - Xác thực tài khoản</title>"
                + "</head>"
                + "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"padding: 20px 0 30px 0;\">"
                + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">"
                + "<tr>"
                + "<td align=\"center\" bgcolor=\"#D14A2D\" style=\"padding: 40px 0 30px 0; color: #ffffff; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">"
                + "Chào mừng đến với Movie X!"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#333131\" style=\"padding: 40px 30px 40px 30px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 24px;\">"
                + "<b>Xin chào, " + fullName + "</b>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"padding: 20px 0 30px 0; color: #ffffff; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">"
                + "Cảm ơn bạn đã đăng ký tài khoản tại Movie X. Để hoàn tất quá trình đăng ký và kích hoạt tài khoản của bạn, vui lòng nhấp vào nút dưới đây."
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td align=\"center\">"
                + "<a href=\"" + hostUrl + "/movie-x/verify?token=" + token + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #D14A2D; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;\">Kích Hoạt Tài Khoản</a>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#D14A2D\" style=\"padding: 30px 30px 30px 30px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"30%\">"
                + "&reg; Movie X, 2024<br/>"
                + "</td>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" align=\"right\" width=\"70%\">"
                + "Design and Developed by Vo Truong Lam"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "</html>";
    }

    private String createForgotMail(String fullName, String otpCode){
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />"
                + "<title>Movie X - Đổi mật khẩu</title>"
                + "</head>"
                + "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"padding: 20px 0 30px 0;\">"
                + "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">"
                + "<tr>"
                + "<td align=\"center\" bgcolor=\"#D14A2D\" style=\"padding: 40px 0 30px 0; color: #ffffff; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">"
                + "Hoàn tất quá trình đổi mật khẩu"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#333131\" style=\"padding: 40px 30px 40px 30px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 24px;\">"
                + "<b>Xin chào, " + fullName + "</b>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style=\"padding: 20px 0 30px 0; color: #ffffff; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">"
                + "Bạn đã yêu cầu đổi mật khẩu cho tài khoản Movie X của mình. Vui lòng sử dụng mã OTP dưới đây để hoàn tất quá trình đổi mật khẩu. Mã OTP này sẽ hết hạn sau 5 phút."
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td align=\"center\" style=\"padding: 20px 0;\">"
                + "<div style=\"display: inline-block; padding: 10px 20px; background-color: #D14A2D; color: white; border-radius: 5px; font-weight: bold; font-size: 24px;\">" + otpCode + "</div>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td bgcolor=\"#D14A2D\" style=\"padding: 30px 30px 30px 30px;\">"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "<tr>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"30%\">"
                + "&reg; Movie X, 2024<br/>"
                + "</td>"
                + "<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" align=\"right\" width=\"70%\">"
                + "Design and Developed by Vo Truong Lam"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</td>"
                + "</tr>"
                + "</table>"
                + "</body>"
                + "</html>";
    }

}
