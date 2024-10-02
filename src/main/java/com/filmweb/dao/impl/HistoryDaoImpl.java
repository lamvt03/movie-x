package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.HistoryDao;
import com.filmweb.entity.History;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HistoryDaoImpl extends AbstractDao<History> implements HistoryDao{
    @Override
    public List<History> findByUserEmail(String email) {
        String jpql = "SELECT o FROM History o WHERE o.user.email = ?1 AND o.video.isActive = 1"
                + " ORDER BY o.viewedAt DESC";
        return super.findMany(History.class, jpql, email);
    }
    @Override
    public List<History> findByUserEmail(String email, int page, int limit) {
        String jpql = "SELECT o FROM History o WHERE o.user.email = ?1 AND o.video.isActive = 1"
                + " ORDER BY o.viewedAt DESC";
        return super.findMany(History.class, page, limit, jpql, email);
    }

    @Override
    public List<History> findByUserEmailAndIsLiked(String email) {
        String jpql = "SELECT o FROM History o WHERE o.user.email = ?1 AND o.isLiked = 1" + " AND o.video.isActive = 1"
                + " ORDER BY o.viewedAt DESC";
        return super.findMany(History.class, jpql, email);
    }

    @Override
    public History findByUserIdAndVideoId(UUID userId, UUID videoId) {
        String jpql = "SELECT o FROM History o WHERE o.user.id = ?1 AND o.video.id = ?2 AND o.video.isActive = 1";
        return super.findOne(History.class, jpql, userId, videoId);
    }
}
