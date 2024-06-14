package com.filmweb.dao;

import com.filmweb.entity.UserVerifiedEmail;

public interface UserVerifiedEmailDao {
    UserVerifiedEmail create(UserVerifiedEmail entity);

    UserVerifiedEmail update(UserVerifiedEmail entity);

    UserVerifiedEmail delete(UserVerifiedEmail entity);

    UserVerifiedEmail findByToken(String token);
}
