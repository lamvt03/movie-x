package com.filmweb.service;

import com.filmweb.dao.OnboardingTokenDao;
import com.filmweb.dao.UserDao;
import com.filmweb.entity.OnboardingToken;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executor;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
@JBossLog
public class OnboardingTokenService {
  
  private static final int ONBOARDING_TOKEN_IN_MINUTES = 60;
  private static final int ONBOARDING_TOKEN_LENGTH = 12;

  @Inject
  private OnboardingTokenDao onboardingTokenDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private NotificationService notificationService;
  
  @Inject
  private UserMapper userMapper;
  
  @Inject
  @Named("sendEmailExecutor")
  private Executor sendEmailExecutor;
  
  public void generateAndSendOnboardingToken(UUID userId) {
    OnboardingToken onboardingToken = onboardingTokenDao.findByUserId(userId);
    if (onboardingToken != null) {
      onboardingTokenDao.delete(onboardingToken);
    }
    
    User user = userDao.findById(userId);
    
    LocalDateTime now = LocalDateTime.now();
    String token = RandomStringUtils.random(ONBOARDING_TOKEN_LENGTH, true, true);
    OnboardingToken onboardingTokenCreated = OnboardingToken.builder()
              .token(token)
              .user(user)
              .createdAt(now)
              .expiredAt(now.plusMinutes(ONBOARDING_TOKEN_IN_MINUTES))
              .build();
    
    onboardingTokenDao.create(onboardingTokenCreated);
    
    sendEmailExecutor.execute(() -> {
      notificationService.sendRegisterEmail(userMapper.toDto(user), token);
    });
  }
}
