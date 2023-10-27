package com.filmweb.dao;

import com.filmweb.entity.Rating;

import java.util.List;

public interface RatingDao {
    List<Rating> findByUserEmail(String email);

    Rating findByUserIdAndVideoId(Long userId, Long videoId);

    Rating create(Rating entity);

    Rating update(Rating entity);
}
