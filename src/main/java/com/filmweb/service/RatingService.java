package com.filmweb.service;

import com.filmweb.dao.RatingDao;
import com.filmweb.entity.Rating;
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
