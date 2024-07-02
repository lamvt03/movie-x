package com.filmweb.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class MailUtils {

    @Inject
    @ConfigProperty(name="mail.host")
    private String host;

    @Inject
    @ConfigProperty(name="mail.port")
    private String port;

    @Inject
    @ConfigProperty(name="mail.username")
    private String username;

    @Inject
    @ConfigProperty(name="mail.password")
    private String password;

    public void sendEmail(String target, String subject, String content, String contentType) throws MessagingException, UnsupportedEncodingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getInstance(properties, auth);


        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        String encodedSubject = "=?UTF-8?B?" + Base64.getEncoder().encodeToString(subject.getBytes(StandardCharsets.UTF_8)) + "?=";
        msg.setSubject(encodedSubject);

        msg.setFrom(new InternetAddress(username));
        InternetAddress[] toAddresses = {new InternetAddress(target)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSentDate(new Date());
        msg.setContent(content, contentType);

        // sends the e-mail
        Transport.send(msg);
    }
}
