package com.filmweb.dao;

import com.filmweb.entity.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findByUserEmail(String email);

    List<Comment> findByVideoId(Long videoId);

    Comment findByUserIdAndVideoId(Long userId, Long videoId);

    Comment findByVideoIdGetUser(Long videoId);

    Comment create(Comment entity);
}
