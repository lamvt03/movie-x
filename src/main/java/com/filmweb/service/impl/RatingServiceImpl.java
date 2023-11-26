package com.filmweb.service.impl;

import com.filmweb.dao.RatingDao;
import com.filmweb.entity.Rating;
import com.filmweb.service.RatingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RatingServiceImpl implements RatingService {
    @Inject
    private RatingDao ratingDao;
    @Override
    public Rating findByUserIdAndVideoId(Long userId, Long videoId) {
        return ratingDao.findByUserIdAndVideoId(userId, videoId);
    }
}
