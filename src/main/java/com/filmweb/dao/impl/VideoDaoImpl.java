package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class VideoDaoImpl extends AbstractDao<Video> implements VideoDao {

    @Override
    public Video findById(Long id) {
        return super.findById(Video.class, id);
    }

    @Override
    public List<Video> findAll() {
        return super.findAll(Video.class);
    }

    @Override
    public List<Video> findAll(int page, int limit) {
        return super.findAll(Video.class, true, page, limit);
    }

    @Override
    public List<Video> findByIsActiveFalseOrderByUpdatedAtDesc(int page, int limit) {
        String jpql = "SELECT v FROM Video v WHERE v.isActive = 0 ORDER BY v.updatedAt DESC";
        return super.findMany(Video.class, page, limit, jpql);
    }

    @Override
    public Video findByHref(String href) {
        String jpql = "SELECT o FROM Video o WHERE o.href = ?1";
        return super.findOne(Video.class, jpql, href);
    }

    @Override
    public List<Video> findTrending(int limit) {
        String jpql = "SELECT o FROM Video o WHERE o.isActive = 1 ORDER BY o.views DESC";
        return super.findMany(Video.class, limit, jpql);
    }

    @Override
    public long count(boolean isActive) {
        return super.count(Video.class, isActive);
    }

    @Override
    public List<Video> findByKeyword(String keyword) {
        String jpql = "SELECT v FROM Video v WHERE v.isActive = 1 AND"
                + " (LOWER(v.title) LIKE ?1 OR LOWER(v.category.name) LIKE ?1 OR LOWER(v.actor) LIKE ?1)"
                + " ORDER BY v.createdAt DESC";
        return super.findMany(Video.class, jpql, "%" + keyword.toLowerCase() + "%");
    }

    @Override
    public List<Video> findByCategoryCodeAndViewsDesc(String categoryCode, int page, int limit) {
        String jpql = "SELECT v FROM Video v JOIN v.category c WHERE v.isActive  = 1 AND c.code = ?1 ORDER BY v.views DESC";
        return super.findMany(Video.class, page, limit, jpql, categoryCode);
    }

    @Override
    public List<Video> findTopYear(int year, int page, int limit) {
        String jpql = "SELECT v FROM Video v WHERE v.isActive = 1 AND v.price = 0 AND YEAR(v.createdAt) = ?1 ORDER BY v.views DESC";
        return super.findMany(Video.class, page, limit, jpql, year);
    }

    @Override
    public List<Video> findLikedVideos(int page, int limit) {
        EntityManager entityManager = super.jpaUtils.getEntityManager();
        String jpql = "SELECT v FROM Video v " +
                        "JOIN v.histories h " +
                        "WHERE v.isActive = true AND h.isLiked = true " +
                        "ORDER BY (" +
                            "SELECT COUNT(h2) as likes FROM History h2 " +
                            "WHERE h2.isLiked = true AND h2.video.id = v.id" +
                        ") DESC";
        try{
            TypedQuery<Video> query = entityManager.createQuery(jpql, Video.class);
            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);
            return query.getResultList();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public long countAllLikedVideos() {
        EntityManager entityManager = super.jpaUtils.getEntityManager();
        String jpql = "SELECT COUNT(v) FROM Video v " +
                "JOIN v.histories h " +
                "WHERE v.isActive = true AND h.isLiked = true ";
        try{
            Query query = entityManager.createQuery(jpql);
            return (long)query.getSingleResult();
        }finally {
            entityManager.close();
        }
    }
}
