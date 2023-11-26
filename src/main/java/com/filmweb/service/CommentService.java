package com.filmweb.service;

import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.Video;

import java.util.List;

public interface CommentService {
    List<Comment> findByUser(String username);

    List<CommentDto> findByVideoId(Long videoId);

    Comment findByUserIdAndVideoId(Long userId, Long videoId);

    Comment findByVideoIdGetUser(Long videoId);

    Comment create(Long userId, Video video, String content);
}
