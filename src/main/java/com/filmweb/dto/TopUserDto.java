package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TopUserDto {
    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private Boolean isActive;
    private Boolean isAdmin;
    private String image;

    @Setter
    private Long total;
}
