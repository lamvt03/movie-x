package com.moviex.dao;

import com.moviex.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryDao {
    List<Category> findAll();

    Category findBySlug(String slug);

    Category create(Category entity);

    Category update(Category entity);

    Category delete(Category entity);

    long count();

    Category findById(UUID id);
    Category findByOrdinal(long ordinal);
}
