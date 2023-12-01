package com.filmweb.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.security.SecureRandom;

@ApplicationScoped
public class PasswordEncoder {

    @Inject
    private SecureRandom secureRandom;

    public String encode(String password){
        SecureRandom secureRandom = new SecureRandom();
        return new String(BCrypt.with(secureRandom).hash(4, password.toCharArray()));
    }
    public boolean verify(String password, String hashPassword){
        return BCrypt.verifyer().verify(password.getBytes(), hashPassword.getBytes()).verified;
    }

    public static void main(String[] args) {
//        System.out.println(new PasswordEncoder().encode("admin123"));
        System.out.println(new PasswordEncoder().encode("123123"));
    }
}
