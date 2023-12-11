package com.filmweb.dao;

import com.filmweb.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findByCode(String code);

    Category create(Category entity);

    Category update(Category entity);

    Category delete(Category entity);
}
