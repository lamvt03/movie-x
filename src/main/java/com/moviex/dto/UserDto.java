package com.moviex.dto;

import com.moviex.domain.user.UserRegistrationType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto{
    private UUID id;
    private String email;
    private String phone;
    private UserRegistrationType registrationType;
    private String fullName;
    private Boolean isActive;
    private Boolean isAdmin;
    private String image;
    private String totalBalanceAmount;
    private String remainingBalanceAmount;
    private LocalDateTime emailVerifiedAt;
}
