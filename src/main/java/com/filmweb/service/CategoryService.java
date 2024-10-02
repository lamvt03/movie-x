package com.filmweb.service;

import com.filmweb.dao.CategoryDao;
import com.filmweb.dto.CategoryDto;
import com.filmweb.entity.Category;
import com.filmweb.mapper.CategoryMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CategoryService {

    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private CategoryDao categoryDao;
    
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }
    
    public Category findBySlug(String slug) {
        return categoryDao.findBySlug(slug);
    }
    
    public Category create(String name, String code) {
        return categoryDao.create(
                Category.builder()
                        .name(name)
                        .slug(code)
                        .build()
        );
    }
    
    public Category update(String name, String slug) {
        Category category = findBySlug(slug);
        category.setSlug(slug);
        category.setName(name);
        return categoryDao.update(category);
    }
    
    public void delete(String slug) {
        Category category = findBySlug(slug);
        categoryDao.delete(category);
    }
}
