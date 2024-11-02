package com.moviex.service;

import com.moviex.dao.CategoryDao;
import com.moviex.dto.CategoryDto;
import com.moviex.entity.Category;
import com.moviex.mapper.CategoryMapper;
import com.moviex.utils.SlugUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

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
    
    public Category findById(UUID id) {
        return categoryDao.findById(id);
    }
    
    public Category create(String name, String code) {
        return categoryDao.create(
                Category.builder()
                        .name(name)
                        .slug(code)
                        .build()
        );
    }
    
    public Category update(UUID id, String name) {
        Category category = findById(id);
        
        // TODO: make sure slug unique
        var slug = SlugUtils.generateSlug(name);
        category.setSlug(slug);
        category.setName(name);
        return categoryDao.update(category);
    }
    
    public void delete(String slug) {
        Category category = findBySlug(slug);
        categoryDao.delete(category);
    }
}
