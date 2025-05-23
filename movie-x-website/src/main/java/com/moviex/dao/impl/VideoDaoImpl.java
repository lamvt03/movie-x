package com.moviex.dao.impl;

import com.moviex.dao.AbstractDao;
import com.moviex.dao.VideoDao;
import com.moviex.entity.Video;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VideoDaoImpl extends AbstractDao<Video> implements VideoDao {

    @Override
    public Video findById(Long id) {
        return super.findById(Video.class, id);
    }
    
    @Override
    public Video findById(UUID id) {
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
    public Video findBySlug(String slug) {
        String jpql = "SELECT o FROM Video o WHERE o.slug = ?1";
        return super.findOne(Video.class, jpql, slug);
    }
    
    @Override
    public boolean existingBySlug(String slug) {
        String jpql = "SELECT COUNT(v) > 0 FROM Video v WHERE v.slug = ?1";
        return super.existingBy(jpql, slug);
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
    public List<Video> findByCategorySlugAndViewsDesc(String categorySlug, int page, int limit) {
        String jpql = "SELECT v FROM Video v JOIN v.category c WHERE v.isActive  = 1 AND c.slug = ?1 ORDER BY v.views DESC";
        return super.findMany(Video.class, page, limit, jpql, categorySlug);
    }

    @Override
    public List<Video> findTopYear(int year, int page, int limit) {
        String jpql = "SELECT v FROM Video v WHERE v.isActive = 1 AND v.price = 0 AND YEAR(v.createdAt) = ?1 ORDER BY v.views DESC";
        return super.findMany(Video.class, page, limit, jpql, year);
    }

    @Override
    public List<Video> findLikedVideos(int page, int limit) {
        EntityManager entityManager = super.jpaHelper.getEntityManager();
        String jpql = "SELECT v.id, COUNT(h) AS likes FROM Video v " +
                        "JOIN v.histories h " +
                        "WHERE v.isActive = true AND h.isLiked = true " +
                        "GROUP BY v.id " +
                        "ORDER BY likes DESC";
        try{
            Query query = entityManager.createQuery(jpql);
            query.setFirstResult((page-1) * limit);
            query.setMaxResults(limit);
            List<Object[]> rs = query.getResultList();
            if(!rs.isEmpty()){
                List<UUID> ids = rs.stream().map(r -> (UUID)r[0])
                                .toList();
                return ids.stream().map(id -> super.findById(Video.class, id))
                        .toList();
            }
            return List.of();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public long countLikedVideos() {
        EntityManager entityManager = super.jpaHelper.getEntityManager();
        String jpql = "SELECT v.id, COUNT(h) AS likes FROM Video v " +
                "JOIN v.histories h " +
                "WHERE v.isActive = true AND h.isLiked = true " +
                "GROUP BY v.id " +
                "ORDER BY likes DESC";
        try{
            Query query = entityManager.createQuery(jpql);
            List<Object[]> rs = query.getResultList();
            return rs.isEmpty() ? 0 : rs.size();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public long countAllLikedVideos() {
        EntityManager entityManager = super.jpaHelper.getEntityManager();
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
