package com.filmweb.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.security.SecureRandom;

@ApplicationScoped
public class CdiConfig {
    @Produces
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }

}
