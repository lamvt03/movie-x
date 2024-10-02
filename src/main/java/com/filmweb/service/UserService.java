package com.filmweb.service;

import com.filmweb.constant.AppConstant;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.TopUserDto;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {
    @Inject
    private RandomUtils randomUtils;

    @Inject
    private PasswordEncodeService passwordEncodeService;

    @Inject
    private UserDao userDao;

    @Inject
    private UserMapper userMapper;

    public UserDto authenticate(String email, String password) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user)
                .filter(u -> passwordEncodeService.verify(password, u.getPassword()))
                .map(userMapper::toDto)
                .orElse(null);
    }

    public boolean existsByPhone(String phone) {
        User user = userDao.findByPhone(phone);
        return Optional.ofNullable(user).isPresent();
    }

    public boolean existByEmail(String email) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user).isPresent();
    }

    public UserDto findByEmail(String email) {
        return userMapper.toDto(userDao.findByEmail(email));
    }

    public UserDto findById(UUID id) {
        User user = userDao.findById(id);
        return userMapper.toDto(user);

    }

    public UserDto register(String email, String password, String phone, String fullName) {
        Integer avtId = randomUtils.randomAvtId(AppConstant.AVATAR_TOTAL);
        User userEntity = User.builder()
                .email(email)
                .password(passwordEncodeService.encode(password))
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

    public UserDto register(GoogleUser user) {
        User userEntity = User.builder()
                .email(user.getEmail())
                .fullName(user.getName())
                .isAdmin(Boolean.FALSE)
                .isActive(Boolean.TRUE)
                .image(user.getId())
                .build();
        return userMapper.toDto(
                userDao.create(userEntity)
        );
    }

    public UserDto verify(UUID id) {
        User user = userDao.findById(id);
        user.setIsActive(Boolean.TRUE);
        return userMapper.toDto(userDao.update(user));
    }

    public UserDto changePassword(String email, String password) {
        User user = userDao.findByEmail(email);
        if(user != null){
            user.setPassword(passwordEncodeService.encode(password));
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    public UserDto editProfile(String email, String fullName, String phone) {
        User user = userDao.findByEmail(email);
        if (user != null){
            user.setFullName(fullName);
            user.setPhone(phone);
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    public boolean comparePassword(String email, String oldPassword) {
        User user = userDao.findByEmail(email);
        return passwordEncodeService.verify(oldPassword, user.getPassword());
    }

    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDto> findAll(int page, int limit) {
        List<User> users = userDao.findAll(page, limit);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

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
