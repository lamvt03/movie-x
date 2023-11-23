package com.filmweb.service.impl;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.EmailConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.service.EmailService;
import com.filmweb.util.RandomUtil;
import com.filmweb.util.SendEmailUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {
    @Inject
    private SendEmailUtil sendEmailUtil;

    @Override
    public void sendRegisterEmail(ServletContext servletContext,UserDto recipient) throws MessagingException {
        String host = servletContext.getInitParameter(EmailConstant.HOST);
        String port = servletContext.getInitParameter(EmailConstant.PORT);
        String username = servletContext.getInitParameter(EmailConstant.USERNAME);
        String password = servletContext.getInitParameter(EmailConstant.PASSWORD);

        String subject = "THANK FOR YOUR REGISTERING";
        String content = sendEmailUtil.getRegiserMessageContent(recipient.getFullName(), recipient.getToken());
        String contentType = "text/html; charset=utf-8";
        sendEmailUtil.sendEmail(host, port, username, password, recipient.getEmail(), subject, content, contentType);
    }

    @Override
    public void sendForgotEmail(ServletContext servletContext, UserDto recipient, String otp) throws MessagingException {
        String host = servletContext.getInitParameter(EmailConstant.HOST);
        String port = servletContext.getInitParameter(EmailConstant.PORT);
        String username = servletContext.getInitParameter(EmailConstant.USERNAME);
        String password = servletContext.getInitParameter(EmailConstant.PASSWORD);

        String subject = "REQUEST TO RETRIEVE PASSWORD";
        String content = sendEmailUtil.getForgotPasswordMessageContent(recipient.getFullName(), otp);
        String contentType = "text/html; charset=utf-8";
        sendEmailUtil.sendEmail(host, port, username, password, recipient.getEmail(), subject, content, contentType);
    }
}
