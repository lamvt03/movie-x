package com.filmweb.dao;

import com.filmweb.domain.user.UserRegistrationType;
import com.filmweb.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    User findById(UUID id);

    User findByEmail(String email);
    
    User findByEmailAndRegistrationType(String email, UserRegistrationType registrationType);

    User findByPhone(String phone);

    List<User> findAll();

    List<User> findAll(int page, int limit);
    List<User> findAllOrderByTotalBalanceAmountDesc(int page, int limit);

    List<Object[]> findTopUsersAndTotal(int page, int limit);

    User create(User entity);

    User update(User entity);

    User delete(User entity);
}
