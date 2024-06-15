package com.filmweb.dao;

import com.filmweb.utils.JPAUtils;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AbstractDao<T> {

    @Inject
    protected JPAUtils jpaUtils;

    protected String buildSelectAllQuery(String entityName) {
        StringBuilder jpqlBd = new StringBuilder();
        jpqlBd
                .append("SELECT o FROM ")
                .append(entityName)
                .append(" o");
        return jpqlBd.toString();
    }

    protected String buildSelectAllAndIsActiveQuery(String entityName, boolean isActive) {
        StringBuilder jpqlBd = new StringBuilder(buildSelectAllQuery(entityName));
        if (isActive) {
            jpqlBd.append(" WHERE o.isActive = 1");
        } else {
            jpqlBd.append(" WHERE o.isActive = 0");
        }
        jpqlBd.append(" ORDER BY o.createdAt DESC ");
        return jpqlBd.toString();
    }

    //	find by id
    protected T findById(Class<T> clazz, Long id) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            return entityManager.find(clazz, id);
        } finally {
            entityManager.close();
        }
    }

    //	find all
    protected List<T> findAll(Class<T> clazz) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            String jpql = buildSelectAllQuery(clazz.getSimpleName());
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //	find all and pagination
    protected List<T> findAll(Class<T> clazz, int page, int limit) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            String jpql = buildSelectAllQuery(clazz.getSimpleName());
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            query.setFirstResult((page - 1) * limit);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //	find all and is active
    protected List<T> findAll(Class<T> clazz, boolean isActive) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            String jpql = buildSelectAllAndIsActiveQuery(clazz.getSimpleName(), isActive);
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    //	find all and is active and pagination
    protected List<T> findAll(Class<T> clazz, boolean isActive, int page, int limit) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            String jpql = buildSelectAllAndIsActiveQuery(clazz.getSimpleName(), isActive);
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            query.setFirstResult((page - 1) * limit);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    protected long count(Class<T> clazz){
        EntityManager entityManager = jpaUtils.getEntityManager();
        String jpql = "SELECT COUNT(o) FROM " + clazz.getSimpleName() + " o";
        Query query = entityManager.createQuery(jpql, clazz);
        return (long)query.getSingleResult();
    }
    protected long count(Class<T> clazz, boolean isActive){
        EntityManager entityManager = jpaUtils.getEntityManager();
        String jpql = "SELECT COUNT(o) FROM " + clazz.getSimpleName() + " o";
        if (isActive){
            jpql += " WHERE o.isActive = 1";
        }else{
            jpql += " WHERE o.isActive = 0";
        }

        Query query = entityManager.createQuery(jpql, clazz);
        return (long)query.getSingleResult();
    }
    protected long count(Class<T> clazz, String jpql, Object... params){
        EntityManager entityManager = jpaUtils.getEntityManager();
        try{
            Query query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return (long)query.getSingleResult();
        }finally {
            entityManager.close();
        }
    }

    public T create(T entity) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    public T update(T entity) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    public T delete(T entity) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entity = entityManager.merge(entity);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }


    public List<T> findAll(Class<T> clazz, List<Long> ids){
        EntityManager entityManager = jpaUtils.getEntityManager();
        String hql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.id IN :ids";
        try{
            TypedQuery<T> query = entityManager.createQuery(hql, clazz);
            query.setParameter("ids", ids);
            return query.getResultList();
        }finally {
            entityManager.close();
        }

    }
    // find one by custom query
    public T findOne(Class<T> clazz, String jpql, Object... params) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            query.setFirstResult(0);
            query.setMaxResults(1);
            List<T> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            entityManager.close();
        }
    }

    // find many by custom query
    public List<T> findMany(Class<T> clazz, String jpql, Object... params) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    // find many by custom query with page and limit
    public List<T> findMany(Class<T> clazz, int page, int limit, String jpql, Object... params) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            query.setFirstResult((page - 1) * limit);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    // find many by custom query with limit
    public List<T> findMany(Class<T> clazz, int limit, String jpql, Object... params) {
        EntityManager entityManager = jpaUtils.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}