package com.filmweb.service;

import com.filmweb.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;

import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendRegisterEmail(UserDto recipient) throws MessagingException, UnsupportedEncodingException;
    void sendForgotEmail(UserDto recipient, String otp) throws MessagingException, UnsupportedEncodingException;
}
