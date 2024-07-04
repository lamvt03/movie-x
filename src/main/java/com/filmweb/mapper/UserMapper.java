package com.filmweb.mapper;

import com.filmweb.dto.TopUserDto;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public UserDto toDto(User entity){
        if(entity == null){
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getFullName(),
                entity.getIsActive(),
                entity.getIsAdmin(),
                entity.getImage()
        );
    }
    public TopUserDto toTopUserDto(User entity){
        if(entity == null){
            return null;
        }
        return new TopUserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getFullName(),
                entity.getIsActive(),
                entity.getIsAdmin(),
                entity.getImage(),
                0L
        );
    }
}
