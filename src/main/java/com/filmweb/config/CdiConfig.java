package com.filmweb.config;

import com.filmweb.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.security.SecureRandom;

@ApplicationScoped
public class CdiConfig {
    @Produces
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }
}
