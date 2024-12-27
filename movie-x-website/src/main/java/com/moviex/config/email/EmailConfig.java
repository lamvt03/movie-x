package com.moviex.config.email;

import com.moviex.email.model.DefaultEmailMessage;
import com.moviex.email.service.EmailSenderService;
import com.moviex.email.service.GoogleEmailSenderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
public class EmailConfig {
  
  @Produces
  @Default
  public EmailSenderService<DefaultEmailMessage> emailSenderService(@ConfigProperties GoogleEmailConfigurationProperties emailConfigurationProperties) {
    return new GoogleEmailSenderService(emailConfigurationProperties);
  }
}
