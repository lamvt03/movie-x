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
        
        return UserDto.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .phone(entity.getPhone())
            .type(entity.getType())
            .fullName(entity.getFullName())
            .isActive(entity.getIsActive())
            .isAdmin(entity.getIsAdmin())
            .image(entity.getImage())
            .totalBalanceAmount(entity.getTotalBalanceAmount())
            .remainingBalanceAmount(entity.getRemainingBalanceAmount())
            .build();
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
