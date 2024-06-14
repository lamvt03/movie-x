package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record UserDto(
        Long id,
        String email,
        String phone,
        String fullName,
        Boolean isActive,
        Boolean isAdmin,
        Integer avtId
) {}
