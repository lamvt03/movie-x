package com.filmweb.dao;

import com.filmweb.entity.History;

import java.util.List;
import java.util.UUID;

public interface HistoryDao {
    List<History> findByUserEmail(String email);
    List<History> findByUserEmail(String email, int page, int limit);

    List<History> findByUserEmailAndIsLiked(String email);

    History findByUserIdAndVideoId(UUID userId, UUID videoId);

    History create(History entity);

    History update(History entity);
}
