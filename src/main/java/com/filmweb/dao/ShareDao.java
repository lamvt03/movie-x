package com.filmweb.dao;

import com.filmweb.entity.Share;

import java.util.List;

public interface ShareDao {
    List<Share> findByUserEmail(String email);

    Share findByUserIdAndVideoId(Long userId, Long videoId);

    Share create(Share entity);
}
