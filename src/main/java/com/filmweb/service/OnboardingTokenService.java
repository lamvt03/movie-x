package com.filmweb.service;

import com.filmweb.dao.OnboardingTokenDao;
import com.filmweb.dao.UserDao;
import com.filmweb.entity.OnboardingToken;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class OnboardingTokenService {
  
  private static final int ONBOARDING_TOKEN_IN_MINUTES = 60;
  private static final int ONBOARDING_TOKEN_LENGTH = 12;
  
  private final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Inject
  private OnboardingTokenDao onboardingTokenDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private RandomUtils randomUtils;
  
  @Inject
  private MailSenderService mailSenderService;
  
  @Inject
  private UserMapper userMapper;
  
  public void generateAndSendOnboardingToken(UUID userId) {
    OnboardingToken onboardingToken = onboardingTokenDao.findByUserId(userId);
    if (onboardingToken != null) {
      onboardingTokenDao.delete(onboardingToken);
    }
    
    User user = userDao.findById(userId);
    
    LocalDateTime now = LocalDateTime.now();
    String token = randomUtils.randomToken(ONBOARDING_TOKEN_LENGTH);
    OnboardingToken onboardingTokenCreated = OnboardingToken.builder()
              .token(token)
              .user(user)
              .createdAt(now)
              .expiredAt(now.plusMinutes(ONBOARDING_TOKEN_IN_MINUTES))
              .build();
    
    onboardingTokenDao.create(onboardingTokenCreated);
    
    executor.submit(() -> {
      try {
        mailSenderService.sendRegisterEmail(userMapper.toDto(user), token);
      } catch (MessagingException | UnsupportedEncodingException e) {
        log.errorf("FAILED to send register email for user with ID %s", userId);
      }
    });
  }
}
