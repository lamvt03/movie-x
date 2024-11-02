package com.filmweb.service;

import com.filmweb.dao.OtpDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.Otp;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
@JBossLog
public class OtpService {
  
  private static final int OTP_CODE_LENGTH = 6;
  
  private static final int OTP_EXPIRATION_IN_MINUTES = 5;
  
  @Inject
  private OtpDao otpDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private NotificationService notificationService;
  
  @Inject
  @Named("sendEmailExecutor")
  private Executor sendEmailExecutor;
  
  public void generateAndSendOtpCode(UserDto userDto) {
    Otp otp = otpDao.findByUserEmail(userDto.getEmail());
    if (otp != null) {
      otpDao.delete(otp);
    }
    
    String otpCode = RandomStringUtils.random(OTP_CODE_LENGTH, false, true);
    LocalDateTime now = LocalDateTime.now();
    User user = userDao.findById(userDto.getId());
    Otp createdOtp = Otp.builder()
        .code(otpCode)
        .createdAt(now)
        .expiredAt(now.plusMinutes(OTP_EXPIRATION_IN_MINUTES))
        .user(user)
        .build();
    otpDao.create(createdOtp);
    
    sendEmailExecutor.execute(() -> {
      notificationService.sendForgotEmail(userDto, otpCode);
    });
  }
  
  public boolean validateOtpCode(String otpCode) {
    Otp otp =  otpDao.findByOtpCode(otpCode);
    return otp != null &&
        otp.getExpiredAt().isAfter(LocalDateTime.now());
  }
}
