package com.filmweb.service;

import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.utils.EmailTemplateUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.UnsupportedEncodingException;

@ApplicationScoped
@JBossLog
public class NotificationService {
    
    @Inject
    private MailSenderService mailSenderService;
    
    @Inject
    @ConfigProperty(name="host.url")
    private String hostUrl;
    
    public void sendRegisterEmail(UserDto recipient, String token) {
        String subject = "Kích hoạt tài khoản của bạn trên Movie X";
        String content = EmailTemplateUtils.buildRegistrationMail(recipient.getFullName(), token, hostUrl);
        String contentType = "text/html; charset=utf-8";
        
        try {
            mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.errorf("FAILED to send register email for user with ID %s", recipient.getId());
        }
    }
    
    public void sendForgotEmail(UserDto recipient, String otp) {
        String subject = "Yêu cầu đổi mật khẩu tài khoản Movie X";
        String content = EmailTemplateUtils.buildForgotMail(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        
        try {
            mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.errorf("FAILED to send forgot password email for user with ID %s", recipient.getId());
        }
    }
    
    public void sendVideoPurchaseSuccessMail(UserDto recipient, VideoDto video) {
        String subject = "Bạn vừa mua một bộ phim trên Movie X";
        String content = EmailTemplateUtils.buildVideoPurchaseSuccessMail(recipient.getFullName(), video.getTitle(), video.getPrice(), buildVideoWatchUrl(video.getSlug()));
        String contentType = "text/html; charset=utf-8";
        
        try {
            mailSenderService.sendEmail(recipient.getEmail(), subject, content, contentType);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.errorf("FAILED to send video purchase success email for user with ID %s", recipient.getId());
        }
    }
    
    private String buildVideoWatchUrl(String slug) {
        return String.format("%s/movie-x/v/watch/%s", hostUrl, slug);
    }
}
