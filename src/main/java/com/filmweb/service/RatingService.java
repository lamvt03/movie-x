package com.filmweb.service;

import com.filmweb.dao.RatingDao;
import com.filmweb.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RatingService {
    @Inject
    private RatingDao ratingDao;
    
    public Rating findByUserIdAndVideoId(Long userId, Long videoId) {
        return ratingDao.findByUserIdAndVideoId(userId, videoId);
    }
}
