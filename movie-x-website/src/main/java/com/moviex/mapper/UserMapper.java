package com.moviex.mapper;

import static com.moviex.utils.PriceFormatUtils.toFomattedString;

import com.moviex.dto.TopUserDto;
import com.moviex.dto.UserDto;
import com.moviex.entity.User;
import com.moviex.utils.PriceFormatUtils;
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
            .isActive(checkUserIsActive(entity))
            .isAdmin(entity.getIsAdmin())
            .isFakeUser(entity.getIsFakeUser())
            .image(entity.getImage())
            .totalBalanceAmount(entity.getTotalBalanceAmount())
            .remainingBalanceAmount(entity.getRemainingBalanceAmount())
            .formattedTotalBalanceAmount(toFomattedString(entity.getTotalBalanceAmount()))
            .formattedRemainingBalanceAmount(toFomattedString(entity.getRemainingBalanceAmount()))
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
                checkUserIsActive(entity),
                entity.getIsAdmin(),
                entity.getImage(),
                0L
        );
    }
    
    private boolean checkUserIsActive(User entity) {
        if (entity.getDeletedAt() != null) {
            return false;
        }
        
        if (entity.getEmailVerifiedAt() == null) {
            return false;
        }
        
        return true;
    }
}
