package com.filmweb.config;

import jakarta.enterprise.context.Dependent;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "jwt")
@Dependent
@Getter
public class JwtConfigurationProperties {

  String secret;
  Long expirationTime;
}
