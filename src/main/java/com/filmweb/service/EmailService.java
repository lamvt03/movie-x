package com.filmweb.service;

import com.filmweb.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;

public interface EmailService {
    void sendRegisterEmail(ServletContext servletContext, UserDto recipient) throws MessagingException;
    void sendForgotEmail(ServletContext servletContext, UserDto recipient, String otp) throws MessagingException;
}
