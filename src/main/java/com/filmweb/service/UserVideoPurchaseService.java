package com.filmweb.service;

import com.filmweb.api.resp.CheckUserBalanceResp;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.UserVideoPurchaseDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.entity.User;
import com.filmweb.entity.UserVideoPurchase;
import com.filmweb.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class UserVideoPurchaseService {
  
  @Inject
  private UserVideoPurchaseDao userVideoPurchaseDao;
  
  @Inject
  private UserDao userDao;
  
  @Inject
  private VideoDao videoDao;
  
  public boolean checkUserVideoPurchase(UUID userId, UUID videoId) {
    var userVideoPurchase = userVideoPurchaseDao.findByUserIdAndVideoId(userId, videoId);
    return userVideoPurchase != null;
  }
  
  public CheckUserBalanceResp checkUserBalanceToPurchaseVideo(UUID userId, UUID videoId) {
    var user = userDao.findById(userId);
    var video = videoDao.findById(videoId);
    
    if (video == null) {
      return CheckUserBalanceResp.builder()
        .isSuccess(false)
        .canPurchase(false)
        .build();
    }
    
    return CheckUserBalanceResp.builder()
        .isSuccess(true)
        .canPurchase(user.getRemainingBalanceAmount() >= video.getPrice())
        .build();
  }
  
  public void createUserVideoPurchase(User user, Video video, Long amount) {
    userVideoPurchaseDao.create(
        UserVideoPurchase.builder()
            .user(user)
            .video(video)
            .purchaseAmount(amount)
            .build()
    );
  }
}
