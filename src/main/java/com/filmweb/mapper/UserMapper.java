package com.filmweb.mapper;

import com.filmweb.dto.TopUserDto;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    private static final String PHONE_NOT_UPDATE_MESSAGE = "Chưa cập nhật";

    public UserDto toDto(User entity){
        
        if(entity == null){
            return null;
        }
        
        return UserDto.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .phone(entity.getPhone() != null ? entity.getPhone() : PHONE_NOT_UPDATE_MESSAGE)
            .registrationType(entity.getRegistrationType())
            .fullName(entity.getFullName())
            .isActive(entity.getIsActive())
            .isAdmin(entity.getIsAdmin())
            .image(entity.getImage())
            .totalBalanceAmount(entity.getTotalBalanceAmount())
            .remainingBalanceAmount(entity.getRemainingBalanceAmount())
            .emailVerifiedAt(entity.getEmailVerifiedAt())
            .build();
    }
    
    public TopUserDto toTopUserDto(User entity){
        
        if(entity == null){
            return null;
        }
        
        return new TopUserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhone() != null ? entity.getPhone() : PHONE_NOT_UPDATE_MESSAGE,
                entity.getFullName(),
                entity.getIsActive(),
                entity.getIsAdmin(),
                entity.getImage(),
                0L
        );
    }
}
