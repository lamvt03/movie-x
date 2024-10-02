package com.filmweb.dao;

import com.filmweb.entity.Otp;
import com.filmweb.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OtpDao extends AbstractDao<Otp>{
  
  public Otp findByUserEmail(String email) {
    String jpql = "SELECT o FROM Otp o JOIN o.user c WHERE o.user.email = ?1";
    return super.findOne(Otp.class, jpql, email);
  }
  
  public Otp findByOtpCode(String code) {
    String jpql = "SELECT o FROM Otp o WHERE o.code = ?1";
    return super.findOne(Otp.class, jpql, code);
  }
}
