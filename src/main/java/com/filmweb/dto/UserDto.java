package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private String token;
    private Boolean isActive;
    private Boolean isAdmin;
}
