package com.moviex.service;

import com.moviex.dao.OnboardingTokenDao;
import com.moviex.dao.UserDao;
import com.moviex.entity.OnboardingToken;
import com.moviex.entity.User;
import com.moviex.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;
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
    
    notificationService.sendEmailVerification(userMapper.toDto(user), token);
  }
}
