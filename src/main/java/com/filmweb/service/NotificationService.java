package com.filmweb.service;

import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.utils.EmailTemplateUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.UnsupportedEncodingException;

@ApplicationScoped
public class NotificationService {
    @Inject
    private MailSenderService mailSenderService;
    

    @Inject
    @ConfigProperty(name="host.url")
    private String hostUrl;
    
    public void sendRegisterEmail(UserDto recipient, String token) throws MessagingException, UnsupportedEncodingException {
        String subject = "Kích hoạt tài khoản của bạn trên Movie X";
        String content = EmailTemplateUtils.buildRegistrationMail(recipient.getFullName(), token, hostUrl);
        String contentType = "text/html; charset=utf-8";
        mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
    }
    
    public void sendForgotEmail(UserDto recipient, String otp) throws MessagingException, UnsupportedEncodingException {
        String subject = "Yêu cầu đổi mật khẩu tài khoản Movie X";
        String content = EmailTemplateUtils.buildForgotMail(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
    }
    
    public void sendVideoPurchaseSuccessMail(UserDto recipient, VideoDto video) throws MessagingException, UnsupportedEncodingException {
        String subject = "Bạn vừa mua một bộ phim trên Movie X";
        String content = EmailTemplateUtils.buildVideoPurchaseSuccessMail(recipient.getFullName(), video.getTitle(), video.getPrice(), buildVideoDetailUrl(video.getSlug()));
        String contentType = "text/html; charset=utf-8";
        mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
    }
    
    private String buildVideoDetailUrl(String slug) {
        return String.format("%s/movie-x/v/detail/%s", hostUrl, slug);
    }
    
}
