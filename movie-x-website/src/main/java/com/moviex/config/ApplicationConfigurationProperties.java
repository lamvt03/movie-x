package com.moviex.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "app")
@ApplicationScoped
@Getter
public class ApplicationConfigurationProperties {
  
  String hostUrl;
}
