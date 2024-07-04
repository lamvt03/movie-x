package com.filmweb.service;

import com.filmweb.dto.CategoryDto;
import com.filmweb.entity.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();

    Category findByCode(String code);

    Category create(String name, String code);
    Category update(String name, String code);

    void delete(String code);
}
