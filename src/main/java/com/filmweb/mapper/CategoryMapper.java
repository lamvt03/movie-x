package com.filmweb.mapper;

import com.filmweb.dto.CategoryDto;
import com.filmweb.entity.Category;
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
