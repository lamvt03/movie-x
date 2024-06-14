package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.entity.UserVerifiedEmail;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserVerifiedEmailDaoImpl extends AbstractDao<UserVerifiedEmail> implements UserVerifiedEmailDao {

    @Override
    public UserVerifiedEmail findByToken(String token) {
        String hql = "SELECT e FROM UserVerifiedEmail e WHERE e.token = ?1 ORDER BY e.expiredAt DESC";
        return super.findOne(UserVerifiedEmail.class, hql, token);
    }
}
