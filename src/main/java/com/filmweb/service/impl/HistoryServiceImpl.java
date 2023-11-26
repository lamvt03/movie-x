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
    public History create(UserDto userDto, Video video) {
        History history = historyDao.findByUserIdAndVideoId(userDto.getId(), video.getId());
        if(history == null){
            User user = userDao.findById(userDto.getId());
            history = new History();
            history.setUser(user);
            history.setVideo(video);
            history.setViewedAt(new Timestamp(System.currentTimeMillis()));
            history.setIsLiked(Boolean.FALSE);
             return historyDao.create(history);
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
