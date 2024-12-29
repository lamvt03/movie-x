package com.moviex.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

import java.security.SecureRandom;

@ApplicationScoped
public class PasswordEncodeService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    
    public String encode(String password) {
        return new String(BCrypt.with(SECURE_RANDOM).hash(4, password.toCharArray()));
    }
    
    public boolean verify(String password, String hashPassword){
        return BCrypt.verifyer().verify(password.getBytes(), hashPassword.getBytes()).verified;
    }
}
