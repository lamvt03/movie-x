package com.filmweb.config;

import jakarta.enterprise.context.Dependent;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "mail")
@Dependent
@Getter
public class MailConfigurationProperties {
  
  String host;
  String port;
  String username;
  String password;
}
