package com.filmweb.service.impl;

import com.filmweb.dao.CategoryDao;
import com.filmweb.dto.CategoryDto;
import com.filmweb.entity.Category;
import com.filmweb.mapper.CategoryMapper;
import com.filmweb.service.CategoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {

    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public Category findByCode(String code) {
        return categoryDao.findByCode(code);
    }

    @Override
    public Category create(String name, String code) {
        return categoryDao.create(
                Category.builder()
                        .name(name)
                        .code(code)
                        .build()
        );
    }

    @Override
    public Category update(String name, String code) {
        Category category = findByCode(code);
        category.setCode(code);
        category.setName(name);
        return categoryDao.update(category);
    }

    @Override
    public void delete(String code) {
        Category category = findByCode(code);
        categoryDao.delete(category);
    }
}
