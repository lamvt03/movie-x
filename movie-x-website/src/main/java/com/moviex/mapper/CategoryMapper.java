package com.moviex.mapper;

import com.moviex.dto.CategoryDto;
import com.moviex.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class CategoryMapper {

    public CategoryDto toDto(Category entity){
        if (entity == null)
            return null;
        return new CategoryDto(
            entity.getId(),
            entity.getName(),
            entity.getSlug(),
            Date.from(entity.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .toInstant())
        );
    }
}
