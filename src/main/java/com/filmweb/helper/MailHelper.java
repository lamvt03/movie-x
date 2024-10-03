package com.filmweb.helper;

import com.filmweb.config.MailConfigurationProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.microprofile.config.inject.ConfigProperties;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class MailHelper {
    
    @Inject
    @ConfigProperties(prefix = "mail")
    private MailConfigurationProperties mailConfigurationProperties;

    public void sendEmail(String target, String subject, String content, String contentType) throws MessagingException, UnsupportedEncodingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailConfigurationProperties.getHost());
        properties.put("mail.smtp.port", mailConfigurationProperties.getPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", mailConfigurationProperties.getHost());

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    mailConfigurationProperties.getUsername(),
                    mailConfigurationProperties.getPassword()
                );
            }
        };
        Session session = Session.getInstance(properties, auth);
        
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        String encodedSubject = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(subject.getBytes(StandardCharsets.UTF_8)) + "?=";
        msg.setSubject(encodedSubject);

        msg.setFrom(new InternetAddress(mailConfigurationProperties.getUsername()));
        InternetAddress[] toAddresses = {new InternetAddress(target)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSentDate(new Date());
        msg.setContent(content, contentType);

        // sends the e-mail
        Transport.send(msg);
    }
}
