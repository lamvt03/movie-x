package com.moviex.dao.impl;

import com.moviex.dao.AbstractDao;
import com.moviex.dao.CommentDao;
import com.moviex.entity.Comment;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    @Override
    public List<Comment> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Comment o WHERE o.user.email = ?1 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Comment.class, jpql, email);
    }

    @Override
    public List<Comment> findByVideoId(UUID videoId, int page, int limit) {
        String jpql = "SELECT o FROM Comment o WHERE o.video.id= ?1 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Comment.class,page, limit, jpql, videoId);
    }

    @Override
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
        String jpql = "SELECT o FROM Comment o WHERE o.user.id = ?1 o.video.id = ?2 AND o.video.isActive = 1";
        return super.findOne(Comment.class, jpql, userId, videoId);
    }

    @Override
    public long count() {
        return super.count(Comment.class);
    }

    @Override
    public List<Comment> findManyByVideoHref(String href, int page, int limit) {
        String jpql = "SELECT o FROM Comment o WHERE o.video.isActive = 1 AND o.video.href = ?1 ORDER BY o.createdAt DESC";
        return super.findMany(Comment.class , page, limit, jpql, href);
    }

    @Override
    public long countByVideoHref(String href) {
        String jpql = "SELECT COUNT(o) FROM Comment o WHERE o.video.isActive = 1 AND o.video.href = ?1";
        return super.count(Comment.class, jpql, href);
    }

    @Override
    public List<Comment> findAllOrderByDesc(int page, int limit) {
        String jpql = "SELECT c FROM Comment c WHERE c.video.isActive = 1 ORDER BY c.createdAt DESC";
        return super.findMany(Comment.class, page, limit, jpql);
    }
}
