package com.filmweb.dao;

import com.filmweb.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User findById(Long id);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByToken(String token);


    List<User> findAll();

    List<User> findAll(int page, int limit);

    User create(User entity);

    User update(User entity);

    User delete(User entity);
}
