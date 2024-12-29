package com.moviex.email.service;

import com.moviex.email.config.EmailProperties;
import com.moviex.email.exception.SendEmailException;
import com.moviex.email.model.DefaultEmailMessage;
import com.moviex.template.exception.TemplateProcessException;
import com.moviex.template.service.TemplateService;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class MailtrapSenderService implements EmailSenderService <DefaultEmailMessage> {
  private static final String HTML_CONTENT_TYPE = "text/html; charset=utf-8";
  
  private final Properties properties;
  private final Authenticator authenticator;
  private final TemplateService templateService;
  
  public MailtrapSenderService(
      EmailProperties emailProperties,
      TemplateService templateService
  ) {
    // sets SMTP server properties
    properties = new Properties();
    properties.put("mail.smtp.host", emailProperties.getHost());
    properties.put("mail.smtp.port", emailProperties.getPort());
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.ssl.trust", emailProperties.getHost());
    
    // creates a new session with an authenticator
    authenticator = new Authenticator() {
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(
            emailProperties.getUsername(),
            emailProperties.getPassword()
        );
      }
    };
    
    this.templateService = templateService;
  }
  
  @Override
  public void sendEmail(DefaultEmailMessage message) throws SendEmailException {
    Session session = Session.getInstance(properties, authenticator);
    
    // creates a new e-mail message
    Message msg = new MimeMessage(session);
    
    try {
      // Allow Vietnamese subject
      String encodedSubject = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(message.getSubject().getBytes(StandardCharsets.UTF_8)) + "?=";
      msg.setSubject(encodedSubject);
      
      msg.setFrom(new InternetAddress(message.getFrom()));
      InternetAddress[] toAddresses = {new InternetAddress(message.getTo())};
      msg.setRecipients(Message.RecipientType.TO, toAddresses);
      msg.setSentDate(new Date());
      msg.setContent(templateService.processTemplate(message.getTemplateId(), message.getTags()), HTML_CONTENT_TYPE);
      
      // sends the e-mail
      Transport.send(msg);
    } catch (MessagingException | TemplateProcessException e) {
      throw new SendEmailException(e);
    }
  }
}
