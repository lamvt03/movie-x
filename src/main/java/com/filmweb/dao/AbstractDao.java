//package com.filmweb.dao;
//
//import com.filmweb.util.JPAUtil;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import jakarta.persistence.StoredProcedureQuery;
//import jakarta.persistence.TypedQuery;
//
//import java.util.List;
//import java.util.Map;
//
//public class AbstractDao<T> {
//
//    //	tìm bằng id
//    public T findById(Class<T> clazz, Integer id) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            return entityManager.find(clazz, id);
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả
//    public List<T> findAll(Class<T> clazz, boolean existIsActive) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 1");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả và phân trang
//    public List<T> findAll(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 1");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            query.setFirstResult((pageNumber - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public List<T> findAllUser(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            query.setFirstResult((pageNumber - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả cho user và loại bỏ admin
//    public List<T> findAllForUser(Class<T> clazz, boolean existIsActive) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 1 AND isAdmin = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả cho user và loại bỏ admin và phân trang
//    public List<T> findAllForUser(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 1 AND isAdmin = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            query.setFirstResult((pageNumber - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả cho video đã ngưng hoạt động
//    public List<T> findAllVideoDelete(Class<T> clazz, boolean existIsActive) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm tất cả cho video đã ngưng hoạt động và phân trang
//    public List<T> findAllVideoDelete(Class<T> clazz, boolean existIsActive, int pageNumber, int pageSize) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            String entityName = clazz.getSimpleName();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT o FROM ").append(entityName).append(" o");
//            if (existIsActive) {
//                sql.append(" WHERE isActive = 0");
//            }
//            TypedQuery<T> query = entityManager.createQuery(sql.toString(), clazz);
//            query.setFirstResult((pageNumber - 1) * pageSize);
//            query.setMaxResults(pageSize);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm một dữ liệu
//    public T findOne(Class<T> clazz, String sql, Object... params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            TypedQuery<T> query = entityManager.createQuery(sql, clazz);
//            for (int i = 0; i < params.length; i++) {
//                query.setParameter(i, params[i]);
//            }
//            List<T> result = query.getResultList();
//            return result.isEmpty() ? null : result.get(0);
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm một list dữ liệu
//    public List<T> findMany(Class<T> clazz, String sql, Object... params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            TypedQuery<T> query = entityManager.createQuery(sql, clazz);
//            for (int i = 0; i < params.length; i++) {
//                query.setParameter(i, params[i]);
//            }
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    //	tìm một list dữ liệu video trending
//    public List<T> findManyMaxResult(Class<T> clazz, String sql, int maxResult, Object... params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            TypedQuery<T> query = entityManager.createQuery(sql, clazz);
//            query.setMaxResults(maxResult);
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<Object[]> findManyByNativeQuery(String sql, Object... params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            Query query = entityManager.createNativeQuery(sql);
//            for (int i = 0; i < params.length; i++) {
//                query.setParameter(i, params[i]);
//            }
//            return query.getResultList();
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public T create(T entity) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(entity);
//            entityManager.getTransaction().commit();
//            return entity;
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw new RuntimeException(e);
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public T update(T entity) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//            entity = entityManager.merge(entity);
//            entityManager.getTransaction().commit();
//            return entity;
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw new RuntimeException(e);
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public T delete(T entity) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        try {
//            entityManager.getTransaction().begin();
//            entity = entityManager.merge(entity);
//            entityManager.remove(entity);
//            entityManager.getTransaction().commit();
//            return entity;
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw new RuntimeException(e);
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public List<T> callStored(String namedStored, Map<String, Object> params) {
//        EntityManager entityManager = JPAUtil.getEntityManager();
//        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery(namedStored);
//        params.forEach((key, value) -> query.setParameter(key, value));
//        return (List<T>) query.getResultList();
//    }
//
//}