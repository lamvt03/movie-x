package com.filmweb.service.impl;

import com.filmweb.dao.HistoryDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.History;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.service.HistoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Timestamp;

@ApplicationScoped
public class HistoryServiceImpl implements HistoryService {

    @Inject
    private HistoryDao historyDao;

    @Inject
    private UserDao userDao;

    @Inject
    private VideoDao videoDao;

    @Override
    public History create(Long userId, Long videoId) {
        History history = historyDao.findByUserIdAndVideoId(userId, videoId);
        if(history == null){
            User user = userDao.findById(userId);
            Video video = videoDao.findById(videoId);
            return historyDao.create(
                    History.builder()
                            .user(user)
                            .video(video)
                            .viewedAt(new Timestamp(System.currentTimeMillis()))
                            .isLiked(Boolean.FALSE)
                            .build()
            );
        }
        return history;
    }

    @Override
    public boolean updateLike(Long userId, String href) {
        Video video = videoDao.findByHref(href);
        History history = historyDao.findByUserIdAndVideoId(userId, video.getId());
        if (history.getIsLiked() == Boolean.FALSE){
            history.setIsLiked(Boolean.TRUE);
            history.setLikedAt(new Timestamp(System.currentTimeMillis()));
        }else{
            history.setIsLiked(Boolean.FALSE);
            history.setLikedAt(null);
        }
        History updateHistory = historyDao.update(history);
        return updateHistory.getIsLiked();
    }
}
