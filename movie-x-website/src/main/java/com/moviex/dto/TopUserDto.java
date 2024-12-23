package com.moviex.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TopUserDto {
    private UUID id;
    private String email;
    private String phone;
    private String fullName;
    private Boolean isActive;
    private Boolean isAdmin;
    private String image;

    @Setter
    private Long total;
}
