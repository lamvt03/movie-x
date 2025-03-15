package com.moviex.dao;

import com.moviex.domain.user.UserRegistrationType;
import com.moviex.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    User findById(UUID id);

    User findByEmail(String email);
    
    boolean existingByEmail(String email);
    
    boolean existingByPhone(String phone);
    
    User findByEmailAndRegistrationType(String email, UserRegistrationType registrationType);

    List<User> findAll();

    List<User> findAll(int page, int limit);
    List<User> findAllOrderByTotalBalanceAmountDesc(int page, int limit);

    User create(User entity);

    User update(User entity);

    User delete(User entity);
}
