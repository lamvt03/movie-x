package com.moviex.dao;

import com.moviex.entity.Share;

import java.util.List;

public interface ShareDao {
    List<Share> findByUserEmail(String email);

    Share findByUserIdAndVideoId(Long userId, Long videoId);

    Share create(Share entity);
}
