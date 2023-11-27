package com.filmweb.service;

import com.filmweb.dto.UserDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Video;

public interface HistoryService {

    History create (Long userId, Long videoId);

    boolean updateLike(Long userId, String href);
}
