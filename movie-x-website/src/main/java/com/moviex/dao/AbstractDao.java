package com.moviex.dao;

import com.moviex.helper.JPAHelper;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class AbstractDao<T> {
    
    private static final String SELECT_ALL_QUERY_PATTERN = "SELECT e FROM %s e";
    private static final String SELECT_ALL_WHERE_IS_ACTIVE_QUERY_PATTERN = "SELECT e FROM %s e WHERE e.isActive = %s";
    private static final String COUNT_WHERE_IS_ACTIVE_QUERY_PATTERN = "SELECT COUNT(e) FROM %s e WHERE e.isActive = %s";

    @Inject
    protected JPAHelper jpaHelper;

    //	find by id
    protected T findById(Class<T> clazz, Long id) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        return entityManager.find(clazz, id);
      }
    }
    
    protected T findById(Class<T> clazz, UUID id) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        return entityManager.find(clazz, id);
      }
    }

    //	find all
    protected List<T> findAll(Class<T> clazz) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        var jpql = String.format(SELECT_ALL_QUERY_PATTERN, clazz.getSimpleName());
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        return query.getResultList();
      }
    }

    //	find all and pagination
    protected List<T> findAll(Class<T> clazz, int page, int limit) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        String jpql = String.format(SELECT_ALL_QUERY_PATTERN, clazz.getSimpleName());
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultList();
      }
    }

    //	find all and is active
    protected List<T> findAll(Class<T> clazz, boolean isActive) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        String jpql = String.format(SELECT_ALL_WHERE_IS_ACTIVE_QUERY_PATTERN, clazz.getSimpleName(), isActive);
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        return query.getResultList();
      }
    }

    //	find all and is active and pagination
    protected List<T> findAll(Class<T> clazz, boolean isActive, int page, int limit) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        String jpql = String.format(SELECT_ALL_WHERE_IS_ACTIVE_QUERY_PATTERN, clazz.getSimpleName(), isActive);
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultList();
      }
    }

    protected long count(Class<T> clazz){
        EntityManager entityManager = jpaHelper.getEntityManager();
        String jpql = "SELECT COUNT(o) FROM " + clazz.getSimpleName() + " o";
        Query query = entityManager.createQuery(jpql, clazz);
        return (long)query.getSingleResult();
    }
    protected long count(Class<T> clazz, boolean isActive){
        EntityManager entityManager = jpaHelper.getEntityManager();
        String jpql = String.format(COUNT_WHERE_IS_ACTIVE_QUERY_PATTERN, clazz.getSimpleName(), isActive);
        Query query = entityManager.createQuery(jpql, clazz);
        return (long)query.getSingleResult();
    }
    protected long count(Class<T> clazz, String jpql, Object... params){
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        Query query = entityManager.createQuery(jpql, clazz);
        for (int i = 0; i < params.length; i++) {
          query.setParameter(i + 1, params[i]);
        }
        return (long) query.getSingleResult();
      }
    }

    public T create(T entity) {
        EntityManager entityManager = jpaHelper.getEntityManager();
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
        EntityManager entityManager = jpaHelper.getEntityManager();
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
        EntityManager entityManager = jpaHelper.getEntityManager();
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
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        String hql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.id IN :ids";
        TypedQuery<T> query = entityManager.createQuery(hql, clazz);
        query.setParameter("ids", ids);
        return query.getResultList();
      }

    }
    
    // find one by custom query
    public T findOne(Class<T> clazz, String jpql, Object... params) {
        EntityManager entityManager = jpaHelper.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            query.setFirstResult(0);
            query.setMaxResults(1);
            List<T> result = query.getResultList();
            return result.isEmpty() ? null : result.getFirst();
        } finally {
            entityManager.close();
        }
    }
    
    protected boolean existingBy(String jpql, Object... params) {
        EntityManager entityManager = jpaHelper.getEntityManager();
        try {
            Query query = entityManager.createQuery(jpql);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return (boolean) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    // find many by custom query
    public List<T> findMany(Class<T> clazz, String jpql, Object... params) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        for (int i = 0; i < params.length; i++) {
          query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
      }
    }
    
    // find many by custom query with page and limit
    public List<T> findMany(Class<T> clazz, int page, int limit, String jpql, Object... params) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        for (int i = 0; i < params.length; i++) {
          query.setParameter(i + 1, params[i]);
        }
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);
        return query.getResultList();
      }
    }
    // find many by custom query with limit
    public List<T> findMany(Class<T> clazz, int limit, String jpql, Object... params) {
      try (EntityManager entityManager = jpaHelper.getEntityManager()) {
        TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
        query.setMaxResults(limit);
        return query.getResultList();
      }
    }
}