package com.filmweb.config;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "mail")
@Dependent
@Getter
public class MailConfigurationProperties {
  
  String host;
  String port;
  String username;
  String password;
}
