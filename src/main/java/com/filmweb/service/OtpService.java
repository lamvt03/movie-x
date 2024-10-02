package com.filmweb.service;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.OtpDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.Otp;
import com.filmweb.entity.User;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class OtpService {
  
  private static final int OTP_CODE_LENGTH = 6;
  
  private static final int OTP_EXPIRATION_IN_MINUTES = 5;
  @Inject
  private RandomUtils randomUtils;
  
  @Inject
  private OtpDao otpDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private MailService mailService;
  
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
    
    try {
      mailService.sendForgotEmail(userDto, otpCode);
    } catch (MessagingException | UnsupportedEncodingException e) {
      log.errorf("FAILED to send forgot email to user with id %s", userDto.getId());
    }
  }
  
  public boolean validateOtpCode(String otpCode) {
    Otp otp =  otpDao.findByOtpCode(otpCode);
    return otp != null &&
        otp.getExpiredAt().isAfter(LocalDateTime.now());
  }
}
