package com.filmweb.service;

import com.filmweb.dto.UserDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Video;

public interface HistoryService {

    History create (UserDto userDto, Video video);

    boolean updateLike(Long userId, String href);
}
