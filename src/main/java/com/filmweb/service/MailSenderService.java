package com.filmweb.service;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.UserVerifiedEmail;
import com.filmweb.utils.EmailTemplateUtils;
import com.filmweb.utils.RandomUtils;
import com.filmweb.utils.MailUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@ApplicationScoped
public class MailSenderService {
    @Inject
    private MailUtils mailUtils;

    @Inject
    private RandomUtils randomUtils;

    @Inject
    @ConfigProperty(name="host.url")
    private String hostUrl;
    
    public void sendRegisterEmail(UserDto recipient, String token) throws MessagingException, UnsupportedEncodingException {
        String subject = "Kích hoạt tài khoản của bạn trên Movie X";
        String content = EmailTemplateUtils.buildRegistrationMail(recipient.getFullName(), token, hostUrl);
        String contentType = "text/html; charset=utf-8";
        mailUtils.sendEmail(recipient.getEmail(), subject, content, contentType);
    }
    
    public void sendForgotEmail(UserDto recipient, String otp) throws MessagingException, UnsupportedEncodingException {
        String subject = "Yêu cầu đổi mật khẩu tài khoản Movie X";
        String content = EmailTemplateUtils.buildForgotMail(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        mailUtils.sendEmail(recipient.getEmail(), subject, content, contentType);
    }
}
