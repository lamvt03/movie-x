package com.moviex.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "jwt")
@ApplicationScoped
@Getter
public class JwtConfigurationProperties {

  String secret;
  Long expirationTime;
}
