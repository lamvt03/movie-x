package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.UserDao;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Override
    public User findById(Long id) {
        return super.findById(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        String jpql = "SELECT o FROM User o WHERE o.email = ?1";
        return super.findOne(User.class, jpql, email);
    }

    @Override
    public User findByPhone(String phone) {
        String jpql = "SELECT o FROM User o WHERE o.phone = ?1";
        return super.findOne(User.class, jpql, phone);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(User.class);
    }

    @Override
    public List<User> findAll(int page, int limit) {
        return super.findAll(User.class, page, limit);
    }

}
