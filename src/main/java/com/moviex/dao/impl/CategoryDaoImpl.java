package com.moviex.dao.impl;

import com.moviex.dao.AbstractDao;
import com.moviex.dao.CategoryDao;
import com.moviex.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
    @Override
    public List<Category> findAll() {
        return super.findAll(Category.class);
    }

    @Override
    public Category findBySlug(String slug) {
        String jpql = "SELECT c from Category c WHERE c.slug = ?1";
        return super.findOne(Category.class, jpql, slug);
    }
    
    @Override
    public boolean existingBySlug(String slug) {
        String jpql = "SELECT COUNT(c) > 0 FROM Category c WHERE c.slug = ?1";
        return super.existingBy(jpql, slug);
    }

    @Override
    public long count() {
        return super.count(Category.class);
    }

    @Override
    public Category findById(UUID id) {
        return super.findById(Category.class, id);
    }
    
    @Override
    public Category findByOrdinal(long ordinal) {
        String jpql = "SELECT c from Category c WHERE c.ordinal = ?1";
        return super.findOne(Category.class, jpql, ordinal);
    }
}
