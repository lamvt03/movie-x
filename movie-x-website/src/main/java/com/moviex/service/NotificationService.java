package com.moviex.service;

import com.moviex.domain.email.EmailMessage;
import com.moviex.dto.UserDto;
import com.moviex.dto.VideoDto;
import com.moviex.exception.SendEmailException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@JBossLog
public class NotificationService {
  
  @Inject
  private MailSenderService mailSenderService;
  
  @Inject
  @ConfigProperty(name = "host.url")
  private String hostUrl;
  
  public void sendEmailVerification(UserDto recipient, String token) {
    var messageBuilder = EmailMessage.builder()
        .to(recipient.getEmail())
        .subject("Kích hoạt tài khoản của bạn trên Movie X")
        .templateId("email_verification");
    
    messageBuilder.tag("full_name", recipient.getFullName());
    messageBuilder.tag("verification_link", buildEmailVerificationLink(token));
    
    try {
      mailSenderService.sendEmail(messageBuilder.build());
    } catch (SendEmailException e) {
      log.errorf("FAILED to send register email for user with ID %s", recipient.getId());
    }
  }
  
  public void sendForgotPasswordEmail(UserDto recipient, String otp) {
    var messageBuilder = EmailMessage.builder()
        .to(recipient.getEmail())
        .subject("Yêu cầu đổi mật khẩu tài khoản Movie X")
        .templateId("password_reset");
    
    messageBuilder.tag("full_name", recipient.getFullName());
    messageBuilder.tag("otp_code", otp);
    
    try {
      mailSenderService.sendEmail(messageBuilder.build());
    } catch (SendEmailException e) {
      log.errorf("FAILED to send forgot password email for user with ID %s", recipient.getId());
    }
  }
  
  public void sendVideoPurchasedEmail(UserDto recipient, VideoDto video) {
    var messageBuilder = EmailMessage.builder()
        .to(recipient.getEmail())
        .subject("Bạn vừa mua một bộ phim trên Movie X")
        .templateId("video_purchase");
    
    messageBuilder.tag("full_name", recipient.getFullName());
    messageBuilder.tag("movie_name", video.getTitle());
    messageBuilder.tag("price", video.getFormattedPrice());
    messageBuilder.tag("movie_watch_url", buildVideoWatchUrl(video.getSlug()));
    
    try {
      mailSenderService.sendEmail(messageBuilder.build());
    } catch (SendEmailException e) {
      log.errorf("FAILED to send video purchase success email for user with ID %s", recipient.getId());
    }
  }
  
  private String buildEmailVerificationLink(String token) {
    return String.format("%s/movie-x/verify?token=%s", hostUrl, token);
  }
  
  private String buildVideoWatchUrl(String slug) {
    return String.format("%s/movie-x/v/watch/%s", hostUrl, slug);
  }
}
