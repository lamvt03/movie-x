package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.CommentDao;
import com.filmweb.entity.Comment;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    @Override
    public List<Comment> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Comment o WHERE o.user.email = ?0 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Comment.class, jpql, email);
    }

    @Override
    public List<Comment> findByVideoId(Long videoId) {
        String jpql = "SELECT o FROM Comment o WHERE o.video.id= ?1 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Comment.class, jpql, videoId);
    }

    @Override
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
        String jpql = "SELECT o FROM Comment o WHERE o.user.id = ?0 o.video.id = ?1 AND o.video.isActive = 1";
        return super.findOne(Comment.class, jpql, userId, videoId);
    }

    @Override
    public Comment findByVideoIdGetUser(Long videoId) {
        return null;
    }
}
