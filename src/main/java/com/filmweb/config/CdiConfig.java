package com.filmweb.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.krazo.servlet.KrazoServletContextListener;

import java.security.SecureRandom;

@ApplicationScoped
public class CdiConfig {
    @Produces
    public SecureRandom secureRandom(){
        return new SecureRandom();
    }

}
