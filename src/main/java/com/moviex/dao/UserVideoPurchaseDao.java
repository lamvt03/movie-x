package com.moviex.dao;

import com.moviex.entity.UserVideoPurchase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UserVideoPurchaseDao extends AbstractDao<UserVideoPurchase> {
  
  public UserVideoPurchase findByUserIdAndVideoId(UUID userId, UUID videoId) {
    String jpql = "SELECT o FROM UserVideoPurchase o WHERE o.user.id = ?1 AND o.video.id = ?2";
    return super.findOne(UserVideoPurchase.class, jpql, userId, videoId);
  }
}
