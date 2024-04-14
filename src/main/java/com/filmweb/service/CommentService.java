package com.filmweb.service;

import com.filmweb.api.CommentListResp;
import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.Video;

import java.util.List;

public interface CommentService {
    List<Comment> findByUser(String username);

    List<CommentDto> findByVideoId(Long videoId, int page, int limit);

    Comment findByUserIdAndVideoId(Long userId, Long videoId);

    Comment findByVideoIdGetUser(Long videoId);

    Comment create(Long userId, Long videoId, String content);

    long count();

    CommentListResp loadMoreComments(String href, int page, int limit);

    int getLastPageByVideoHref(String href, int limit);
}
