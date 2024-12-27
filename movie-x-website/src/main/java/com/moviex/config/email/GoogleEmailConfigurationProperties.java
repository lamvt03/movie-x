package com.moviex.config.email;

import com.moviex.email.config.EmailConfigurationProperties;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "mail")
@ApplicationScoped
@Getter
public class GoogleEmailConfigurationProperties implements EmailConfigurationProperties {
  
  String host;
  String port;
  String username;
  String password;
  String defaultFromEmail;
}
