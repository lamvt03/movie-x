package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.CategoryDao;
import com.filmweb.entity.Category;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {
    @Override
    public List<Category> findAll() {
        return super.findAll(Category.class);
    }

    @Override
    public Category findByCode(String code) {
        String jpql = "SELECT c from Category c WHERE c.code = ?1";
        return super.findOne(Category.class, jpql, code);
    }

    @Override
    public long count() {
        return super.count(Category.class);
    }

    @Override
    public Category findById(long categoryId) {
        return super.findById(Category.class, categoryId);
    }
}
