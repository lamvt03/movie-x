package com.filmweb.service;

import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.UserDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {

    UserDto authenticate(String email, String password);
    boolean existsByPhone(String phone);
    boolean existByEmail(String email);
    UserDto findByEmail(String email);
    UserDto findById(Long id);
    UserDto register(String email, String password, String phone, String fullName);
    UserDto register(GoogleUser user);
    UserDto verify(Long id);

    void sendForgotPasswordMessage(ServletContext servletContext, HttpSession session, UserDto userDto) throws MessagingException;

    UserDto changePassword(String email, String password);

    UserDto editProfile(String email, String fullName, String phone);

    boolean comparePassword(String email, String oldPassword);

    List<UserDto> findAll();
    List<UserDto> findAll(int page, int limit);
}
