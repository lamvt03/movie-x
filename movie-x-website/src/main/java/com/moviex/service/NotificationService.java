package com.moviex.service;

import com.moviex.config.ApplicationConfigurationProperties;
import com.moviex.config.email.GoogleEmailConfigurationProperties;
import com.moviex.dto.UserDto;
import com.moviex.dto.VideoDto;
import com.moviex.email.exception.SendEmailException;
import com.moviex.email.model.DefaultEmailMessage;
import com.moviex.email.service.EmailSenderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.concurrent.Executor;
import lombok.extern.jbosslog.JBossLog;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
@JBossLog
public class NotificationService {
  
  @Inject
  private EmailSenderService<DefaultEmailMessage> mailSenderService;
  
  @Inject
  @Named("sendEmailExecutor")
  private Executor sendEmailExecutor;
  
  @Inject
  @ConfigProperties
  private GoogleEmailConfigurationProperties emailConfigurationProperties;
  
  @Inject
  @ConfigProperties
  private ApplicationConfigurationProperties applicationConfigurationProperties;
  
  public void sendEmailVerification(UserDto recipient, String token) {
    sendEmailExecutor.execute(() -> {
      var messageBuilder = DefaultEmailMessage.builder()
      .from(emailConfigurationProperties.getDefaultFromEmail())
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
    });
  }
  
  public void sendForgotPasswordEmail(UserDto recipient, String otp) {
    sendEmailExecutor.execute(() -> {
      var messageBuilder = DefaultEmailMessage.builder()
      .from(emailConfigurationProperties.getDefaultFromEmail())
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
    });
  }
  
  public void sendVideoPurchasedEmail(UserDto recipient, VideoDto video) {
    sendEmailExecutor.execute(() -> {
      var messageBuilder = DefaultEmailMessage.builder()
      .from(emailConfigurationProperties.getDefaultFromEmail())
      .to(recipient.getEmail())
      .subject("Bạn vừa mua một bộ phim trên Movie X")
      .templateId("video_purchase");
      
      messageBuilder.tag("full_name", recipient.getFullName());
      messageBuilder.tag("movie_name", video.getTitle());
      messageBuilder.tag("price", video.getPrice());
      messageBuilder.tag("movie_watch_url", buildVideoWatchUrl(video.getSlug()));
      
      try {
        mailSenderService.sendEmail(messageBuilder.build());
      } catch (SendEmailException e) {
        log.errorf("FAILED to send video purchase success email for user with ID %s", recipient.getId());
      }
    });
  }
  
  private String buildEmailVerificationLink(String token) {
    return String.format("%s/movie-x/verify?token=%s", applicationConfigurationProperties.getHostUrl(), token);
  }
  
  private String buildVideoWatchUrl(String slug) {
    return String.format("%s/movie-x/v/watch/%s", applicationConfigurationProperties.getHostUrl(), slug);
  }
}
