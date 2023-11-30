package com.filmweb.service.impl;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import com.filmweb.service.EmailService;
import com.filmweb.service.UserService;
import com.filmweb.util.RandomUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Inject
    private RandomUtil randomUtil;
    @Inject
    private UserDao userDao;

    @Inject
    private UserMapper userMapper;

    @Inject
    private EmailService emailService;

    @Override
    public UserDto authenticate(String email, String password) {
        User user = userDao.findByEmailAndPassword(email, password);
        return Optional.ofNullable(user).map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public boolean existsByPhone(String phone) {
        User user = userDao.findByPhone(phone);
        return Optional.ofNullable(user).isPresent();
    }

    @Override
    public boolean existByEmail(String email) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user).isPresent();
    }

    @Override
    public UserDto findByEmail(String email) {
        return userMapper.toDto(userDao.findByEmail(email));
    }

    @Override
    public UserDto register(String email, String password, String phone, String fullName) {
        String token = randomUtil.randomToken(AppConstant.REGISTER_TOKEN_LENGTH);
        User userEntity = User.builder()
                .email(email)
                .password(password)
                .phone(phone)
                .fullName(fullName)
                .token(token)
                .isActive(false)
                .isAdmin(false)
                .build();
        return userMapper.toDto(userDao.create(userEntity));
    }

    @Override
    public UserDto verifyEmail(String token) {
        User user = userDao.findByToken(token);
        if(user != null){
            user.setIsActive(true);
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    @Override
    public void sendForgotPasswordMessage(ServletContext servletContext, HttpSession session, UserDto userDto) throws MessagingException {
        String otp = randomUtil.randomOtpValue(AppConstant.OTP_LENGTH);
        emailService.sendForgotEmail(servletContext, userDto, otp);
        session.setAttribute("otp", otp);
        session.setMaxInactiveInterval(180);
        session.setAttribute("email", userDto.getEmail());
    }

    @Override
    public UserDto changePassword(String email, String password) {
        User user = userDao.findByEmail(email);
        if(user != null){
            user.setPassword(password);
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    @Override
    public UserDto editProfile(String email, String fullName, String phone) {
        User user = userDao.findByEmail(email);
        if (user != null){
            user.setFullName(fullName);
            user.setPhone(phone);
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    @Override
    public boolean comparePassword(String email, String oldPassword) {
        User user = userDao.findByEmail(email);
        return user.getPassword().equals(oldPassword);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDto> findAll(int page, int limit) {
        List<User> users = userDao.findAll(page, limit);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }
}
