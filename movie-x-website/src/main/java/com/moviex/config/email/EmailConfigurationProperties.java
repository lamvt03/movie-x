package com.moviex.config.email;

import com.moviex.email.config.EmailProperties;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "mail")
@ApplicationScoped
@Getter
public class EmailConfigurationProperties implements EmailProperties {
  
  String host;
  String port;
  String username;
  String password;
  String defaultFromEmail;
}
