package com.filmweb.dto;

import com.filmweb.domain.user.UserType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
public class UserDto{
    private UUID id;
    private String email;
    private String phone;
    private UserType type;
    private String fullName;
    private Boolean isActive;
    private Boolean isAdmin;
    private String image;
}
