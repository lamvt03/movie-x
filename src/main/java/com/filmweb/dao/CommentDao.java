package com.filmweb.dao;

import com.filmweb.entity.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findByUserEmail(String email);

    List<Comment> findByVideoId(Long videoId, int page, int limit);

    Comment findByUserIdAndVideoId(Long userId, Long videoId);

    Comment findByVideoIdGetUser(Long videoId);

    Comment create(Comment entity);

    long count();
    List<Comment> findManyByVideoHref(String href, int page, int limit);

    long countByVideoHref(String href);
}
