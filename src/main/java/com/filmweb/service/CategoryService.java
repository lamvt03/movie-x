package com.filmweb.service;

import com.filmweb.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findByCode(String code);

    Category create(String name, String code);
    Category update(String name, String code);

    void delete(String code);
}
