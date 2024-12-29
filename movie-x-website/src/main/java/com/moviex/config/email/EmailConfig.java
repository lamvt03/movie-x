package com.moviex.config.email;

import com.moviex.email.model.DefaultEmailMessage;
import com.moviex.email.service.EmailSenderService;
import com.moviex.email.service.MailtrapSenderService;
import com.moviex.template.service.TemplateService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
public class EmailConfig {
  
  @Produces
  @Default
  public EmailSenderService<DefaultEmailMessage> emailSenderService(
      @ConfigProperties EmailConfigurationProperties emailConfigurationProperties,
      @Named("emailTemplateService") TemplateService templateService
      ) {
    return new MailtrapSenderService(emailConfigurationProperties, templateService);
  }
}
