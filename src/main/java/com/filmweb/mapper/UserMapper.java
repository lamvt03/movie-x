package com.filmweb.mapper;

import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public UserDto toDto(User entity){
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getFullName(),
                entity.getToken(),
                entity.getIsActive(),
                entity.getIsAdmin()
        );
    }
}
