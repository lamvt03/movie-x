package com.filmweb.service.impl;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.EmailConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.UserVerifiedEmail;
import com.filmweb.service.EmailService;
import com.filmweb.utils.RandomUtils;
import com.filmweb.utils.SendEmailUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;

import java.time.LocalDateTime;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {
    @Inject
    private SendEmailUtils sendEmailUtils;

    @Inject
    private RandomUtils randomUtils;

    @Inject
    private UserVerifiedEmailDao verifiedEmailDao;

    @Override
    public void sendRegisterEmail(ServletContext servletContext, UserDto recipient) throws MessagingException {
        String host = servletContext.getInitParameter(EmailConstant.HOST);
        String port = servletContext.getInitParameter(EmailConstant.PORT);
        String username = servletContext.getInitParameter(EmailConstant.USERNAME);
        String password = servletContext.getInitParameter(EmailConstant.PASSWORD);

        UserVerifiedEmail verifiedEmail = new UserVerifiedEmail();
        String token = randomUtils.randomToken(AppConstant.REGISTER_TOKEN_LENGTH);
        verifiedEmail.setToken(token);
        verifiedEmail.setExpiredAt(LocalDateTime.now().plusMinutes(AppConstant.REGISTER_TOKEN_MINUTES));
        verifiedEmail.setUserId(recipient.getId());
        verifiedEmailDao.create(verifiedEmail);

        String subject = "THANK FOR YOUR REGISTERING";
        String content = sendEmailUtils.getRegisterMessageContent(recipient.getFullName(), token);
        String contentType = "text/html; charset=utf-8";
        sendEmailUtils.sendEmail(host, port, username, password, recipient.getEmail(), subject, content, contentType);
    }

    @Override
    public void sendForgotEmail(ServletContext servletContext, UserDto recipient, String otp) throws MessagingException {
        String host = servletContext.getInitParameter(EmailConstant.HOST);
        String port = servletContext.getInitParameter(EmailConstant.PORT);
        String username = servletContext.getInitParameter(EmailConstant.USERNAME);
        String password = servletContext.getInitParameter(EmailConstant.PASSWORD);

        String subject = "REQUEST TO RETRIEVE PASSWORD";
        String content = sendEmailUtils.getForgotPasswordMessageContent(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        sendEmailUtils.sendEmail(host, port, username, password, recipient.getEmail(), subject, content, contentType);
    }
}
