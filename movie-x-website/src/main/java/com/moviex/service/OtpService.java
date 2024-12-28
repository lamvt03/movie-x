package com.moviex.service;

import com.moviex.dao.OtpDao;
import com.moviex.dao.UserDao;
import com.moviex.dto.UserDto;
import com.moviex.entity.Otp;
import com.moviex.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
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
    
    notificationService.sendForgotPasswordEmail(userDto, otpCode);
  }
  
  public boolean validateOtpCode(String otpCode) {
    Otp otp =  otpDao.findByOtpCode(otpCode);
    return otp != null &&
        otp.getExpiredAt().isAfter(LocalDateTime.now());
  }
}
