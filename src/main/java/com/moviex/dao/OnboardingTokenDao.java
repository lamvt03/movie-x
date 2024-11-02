package com.moviex.dao;

import com.moviex.entity.OnboardingToken;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class OnboardingTokenDao extends AbstractDao<OnboardingToken> {
  
  public OnboardingToken findByToken(String token) {
    String jpql = "SELECT o FROM OnboardingToken o  WHERE o.token = ?1";
    return super.findOne(OnboardingToken.class, jpql, token);
  }
  
  public OnboardingToken findByUserId(UUID userId) {
    String jpql = "SELECT o FROM OnboardingToken o JOIN o.user  WHERE o.user.id = ?1";
    return super.findOne(OnboardingToken.class, jpql, userId);
  }
}
