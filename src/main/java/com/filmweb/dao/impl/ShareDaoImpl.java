package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.ShareDao;
import com.filmweb.entity.Share;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ShareDaoImpl extends AbstractDao<Share> implements ShareDao {
    @Override
    public List<Share> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Share o WHERE o.user.email = ?0 AND o.video.isActive = 1 ORDER BY o.createdAt DESC";
        return super.findMany(Share.class, jpql, email);
    }

    @Override
    public Share findByUserIdAndVideoId(Long userId, Long videoId) {
        String sql = "SELECT o FROM Share o WHERE o.user.id = ?0 AND o.video.id = ?1 AND o.video.isActive = 1";
        return super.findOne(Share.class, sql, userId, videoId);
    }
}
