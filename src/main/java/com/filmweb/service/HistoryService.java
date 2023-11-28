package com.filmweb.service;

import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Video;

import java.util.List;

public interface HistoryService {

    History create (Long userId, Long videoId);

    boolean updateLike(Long userId, String href);

    List<History> findByEmail(String email);
    List<VideoDto> findViewedVideoByEmail(String email, int page, int limit);

    List<VideoDto> findFavoriteVideoByEmail(String email);
}
