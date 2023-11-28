package com.filmweb.dao;

import com.filmweb.util.JPAUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;

public class AbstractDao<T> {

    @Inject
    private JPAUtil jpaUtil;

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
        return jpqlBd.toString();
    }

    //	find by id
    protected T findById(Class<T> clazz, Long id) {
        EntityManager entityManager = jpaUtil.getEntityManager();
        try {
            return entityManager.find(clazz, id);
        } finally {
            entityManager.close();
        }
    }

    //	find all
    protected List<T> findAll(Class<T> clazz) {
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
        String jpql = "SELECT COUNT(o) FROM " + clazz.getSimpleName() + " o"
                + " WHERE o.isActive = 1";
        Query query = entityManager.createQuery(jpql, clazz);
        return (long)query.getSingleResult();
    }

    public T create(T entity) {
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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


    //	tìm tất cả cho user và loại bỏ admin
//    public List<T> findAllForUser(Class<T> clazz, boolean existIsActive) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder jpql = new StringBuilder();
//            jpql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                jpql.append(" WHERE isActive = 1 AND isAdmin = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(jpql.toString(), clazz);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }

//    //	tìm tất cả cho user và loại bỏ admin và phân trang
//    public List<T> findAllForUser(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder jpql = new StringBuilder();
//            jpql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                jpql.append(" WHERE isActive = 1 AND isAdmin = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(jpql.toString(), clazz);
//            query.setFirstResult((pageNumber - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }

    // find one by custom query
    public T findOne(Class<T> clazz, String jpql, Object... params) {
        EntityManager entityManager = jpaUtil.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            List<T> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            entityManager.close();
        }
    }

    // find many by custom query
    public List<T> findMany(Class<T> clazz, String jpql, Object... params) {
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
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
        EntityManager entityManager = jpaUtil.getEntityManager();
        try {
            TypedQuery<T> query = entityManager.createQuery(jpql, clazz);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

//    public List<Object[]> findManyByNativeQuery(String jpql, Object... params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            Query query = entityManager.createNativeQuery(jpql);
//            for (int i = 0; i < params.length; i++) {
//                query.setParameter(i, params[i]);
//            }
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }


//    public List<T> callStored(String namedStored, Map<String, Object> params) {
//        EntityManager entityManager = jpaUtil.getEntityManager();
//        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(namedStored);
//        params.forEach((key, value) -> query.setParameter(key, value));
//        return (List<T>) query.getResultList();
//    }

}