package com.moviex.dao.impl;

import com.moviex.dao.AbstractDao;
import com.moviex.dao.RatingDao;
import com.moviex.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RatingDaoImpl extends AbstractDao<Rating> implements RatingDao {
    @Override
    public List<Rating> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Rating o WHERE o.user.email = ?1 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Rating.class, jpql, email);
    }

    @Override
    public Rating findByUserIdAndVideoId(UUID userId, UUID videoId) {
        String jpql = "SELECT o FROM Rating o WHERE o.user.id = ?1 AND o.video.id = ?2 AND o.video.isActive = 1";
        return super.findOne(Rating.class, jpql, userId, videoId);
    }
}
