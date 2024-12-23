package com.moviex.service;

import com.moviex.dao.RatingDao;
import com.moviex.entity.Rating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class RatingService {
    @Inject
    private RatingDao ratingDao;
    
    public Rating findByUserIdAndVideoId(UUID userId, UUID videoId) {
        return ratingDao.findByUserIdAndVideoId(userId, videoId);
    }
}
