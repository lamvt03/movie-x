package com.moviex.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;

@ApplicationScoped
public class PasswordEncodeService {

    public String encode(String password) {
        SecureRandom secureRandom = new SecureRandom();
        return new String(BCrypt.with(secureRandom).hash(4, password.toCharArray()));
    }
    
    public boolean verify(String password, String hashPassword){
        return BCrypt.verifyer().verify(password.getBytes(), hashPassword.getBytes()).verified;
    }
}
