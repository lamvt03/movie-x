package com.moviex.config;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "google.oauth2")
@ApplicationScoped
@Getter
public class GoogleOauth2ConfigurationProperties {
  String clientId;
  String clientSecret;
  String scope;
  String redirectUri;
  String responseType;
  String approvalPrompt;
  String grantType;
}
