package com.moviex.service;

import com.moviex.config.MailConfigurationProperties;
import com.moviex.domain.email.EmailMessage;
import com.moviex.exception.SendEmailException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
public class MailSenderService {
    
    private static final String EMAIL_TEMPLATE_URL_PATTERN = "templates/email/%s.html";
    private static final String EMAIL_TAG_PATTERN = "{{%s}}";
    
    private static final String HTML_CONTENT_TYPE = "text/html; charset=utf-8";
    
    @Inject
    @ConfigProperties(prefix = "mail")
    private MailConfigurationProperties mailConfigurationProperties;
    
    private Properties properties;
    private Authenticator authenticator;
    
    @PostConstruct
    void init() {
        // sets SMTP server properties
        properties = new Properties();
        properties.put("mail.smtp.host", mailConfigurationProperties.getHost());
        properties.put("mail.smtp.port", mailConfigurationProperties.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", mailConfigurationProperties.getHost());
        
        // creates a new session with an authenticator
        authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    mailConfigurationProperties.getUsername(),
                    mailConfigurationProperties.getPassword()
                );
            }
        };
    }
    
    public void sendEmail(EmailMessage message) throws SendEmailException {
        Session session = Session.getInstance(properties, authenticator);
        
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        
        try {
            // Allow Vietnamese subject
            String encodedSubject = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(message.getSubject().getBytes(StandardCharsets.UTF_8)) + "?=";
            msg.setSubject(encodedSubject);
            
            msg.setFrom(new InternetAddress(mailConfigurationProperties.getUsername()));
            InternetAddress[] toAddresses = {new InternetAddress(message.getTo())};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSentDate(new Date());
            msg.setContent(buildEmailContent(message.getTemplateId(), message.getTags()), HTML_CONTENT_TYPE);
            
            // sends the e-mail
            Transport.send(msg);
        } catch (MessagingException | URISyntaxException | IOException e) {
          throw new SendEmailException(e);
        }
    }
    
    private String buildEmailContent(String templateId, Map<String, Object> tags) throws URISyntaxException, IOException {
        var path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(String.format(EMAIL_TEMPLATE_URL_PATTERN, templateId))).toURI());
        final String[] content = {Files.readString(path)};
        
        Optional.ofNullable(tags)
            .ifPresent(t -> {
                t.forEach((key, value) -> {
                  content[0] = content[0].replace(String.format(EMAIL_TAG_PATTERN, key), value.toString());
                });
            });
        
        return content[0];
    }
}
