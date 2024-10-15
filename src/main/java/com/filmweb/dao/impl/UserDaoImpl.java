package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.UserDao;
import com.filmweb.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    @Override
    public User findById(UUID id) {
        return super.findById(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        String jpql = "SELECT o FROM User o WHERE o.deletedAt IS NULL AND o.email = ?1";
        return super.findOne(User.class, jpql, email);
    }

    @Override
    public User findByPhone(String phone) {
        String jpql = "SELECT o FROM User o WHERE o.deletedAt IS NULL AND o.phone = ?1";
        return super.findOne(User.class, jpql, phone);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(User.class);
    }

    @Override
    public List<User> findAll(int page, int limit) {
        return super.findAll(User.class, page, limit);
    }

    @Override
    public List<Object[]> findTopUsersAndTotal(int page, int limit) {
        String hql = "SELECT u, sum(o.vnp_Amount) as total FROM User u "
                +   "JOIN u.orders o "
                +   "GROUP BY u "
                +   "ORDER BY total DESC";
        EntityManager entityManager = super.jpaHelper.getEntityManager();
        Query query = entityManager.createQuery(hql);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultList();
    }

}
