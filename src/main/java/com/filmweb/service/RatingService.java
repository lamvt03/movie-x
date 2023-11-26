package com.filmweb.service;

import com.filmweb.entity.Rating;

public interface RatingService {
    Rating findByUserIdAndVideoId(Long userId, Long videoId);
}
