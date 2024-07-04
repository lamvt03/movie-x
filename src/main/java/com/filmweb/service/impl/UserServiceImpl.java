package com.filmweb.service.impl;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.TopUserDto;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import com.filmweb.service.MailService;
import com.filmweb.service.UserService;
import com.filmweb.utils.PasswordEncoder;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Inject
    private RandomUtils randomUtils;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserDao userDao;

    @Inject
    private UserMapper userMapper;

    @Inject
    private MailService mailService;

    @Override
    public UserDto authenticate(String email, String password) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user)
                .filter(u -> passwordEncoder.verify(password, u.getPassword()))
                .map(userMapper::toDto)
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
    public UserDto findById(Long id) {
        User user = userDao.findById(id);
        return userMapper.toDto(user);

    }

    @Override
    public UserDto register(String email, String password, String phone, String fullName) {
        Integer avtId = randomUtils.randomAvtId(AppConstant.AVATAR_TOTAL);
        User userEntity = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .phone(phone)
                .fullName(fullName)
                .isAdmin(Boolean.FALSE)
                .isActive(Boolean.FALSE)
                .image(AppConstant.IMAGE_PREFIX + avtId + AppConstant.IMAGE_SUFFIX)
                .build();
        return userMapper.toDto(
                userDao.create(userEntity)
        );
    }

    @Override
    public UserDto register(GoogleUser user) {
        User userEntity = User.builder()
                .email(user.email())
                .fullName(user.name())
                .isAdmin(Boolean.FALSE)
                .isActive(Boolean.TRUE)
                .image(user.picture())
                .build();
        return userMapper.toDto(
                userDao.create(userEntity)
        );
    }

    @Override
    public UserDto verify(Long id) {
        User user = userDao.findById(id);
        user.setIsActive(Boolean.TRUE);
        return userMapper.toDto(userDao.update(user));
    }

    @Override
    public UserDto changePassword(String email, String password) {
        User user = userDao.findByEmail(email);
        if(user != null){
            user.setPassword(passwordEncoder.encode(password));
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
        return passwordEncoder.verify(oldPassword, user.getPassword());
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

    @Override
    public List<TopUserDto> findTopUsers(int page, int limit) {
        return userDao.findTopUsersAndTotal(page, limit).stream()
                .map(item -> {
                    TopUserDto topUserDto = userMapper.toTopUserDto((User) item[0]);
                    topUserDto.setTotal((Long) item[1]);
                    return topUserDto;
                })
                .toList();
    }
}
