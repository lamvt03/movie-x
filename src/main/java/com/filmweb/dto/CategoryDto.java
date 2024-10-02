package com.filmweb.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryDto {
    private UUID id;
    private String name;
    private String slug;
    private Date createdAt;
}
