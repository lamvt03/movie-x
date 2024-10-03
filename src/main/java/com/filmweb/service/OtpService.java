package com.filmweb.service;

import com.filmweb.dao.OtpDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.Otp;
import com.filmweb.entity.User;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class OtpService {
  
  private static final int OTP_CODE_LENGTH = 6;
  
  private static final int OTP_EXPIRATION_IN_MINUTES = 5;
  
  private final ExecutorService executor = Executors.newSingleThreadExecutor();
  
  @Inject
  private RandomUtils randomUtils;
  
  @Inject
  private OtpDao otpDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private MailSenderService mailSenderService;
  
  @Transactional
  public void generateAndSendOtpCode(UserDto userDto) {
    Otp otp = otpDao.findByUserEmail(userDto.getEmail());
    if (otp != null) {
      otpDao.delete(otp);
    }
    
    String otpCode = randomUtils.randomOtpValue(OTP_CODE_LENGTH);
    LocalDateTime now = LocalDateTime.now();
    User user = userDao.findById(userDto.getId());
    Otp createdOtp = Otp.builder()
        .code(otpCode)
        .createdAt(now)
        .expiredAt(now.plusMinutes(OTP_EXPIRATION_IN_MINUTES))
        .user(user)
        .build();
    otpDao.create(createdOtp);
    
    executor.submit(() -> {
      try {
        mailSenderService.sendForgotEmail(userDto, otpCode);
      } catch (MessagingException | UnsupportedEncodingException e) {
        log.errorf("FAILED to send forgot password email for user with ID %s", userDto.getId());
      }
    });
  }
  
  @Transactional
  public boolean validateOtpCode(String otpCode) {
    Otp otp =  otpDao.findByOtpCode(otpCode);
    return otp != null &&
        otp.getExpiredAt().isAfter(LocalDateTime.now());
  }
}
