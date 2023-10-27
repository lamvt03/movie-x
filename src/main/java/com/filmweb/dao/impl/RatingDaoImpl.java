package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.RatingDao;
import com.filmweb.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RatingDaoImpl extends AbstractDao<Rating> implements RatingDao {
    @Override
    public List<Rating> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Rating o WHERE o.user.email = ?0 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Rating.class, jpql, email);
    }

    @Override
    public Rating findByUserIdAndVideoId(Long userId, Long videoId) {
        String jpql = "SELECT o FROM Rating o WHERE o.user.id = ?0 AND o.video.id = ?1 AND o.video.isActive = 1";
        return super.findOne(Rating.class, jpql, userId, videoId);
    }
}
