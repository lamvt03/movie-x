package com.filmweb.service;

import com.filmweb.dao.HistoryDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HistoryService {

    @Inject
    private HistoryDao historyDao;

    @Inject
    private UserDao userDao;

    @Inject
    private VideoDao videoDao;

    @Inject
    private VideoMapper videoMapper;

    public History createNewHistory(UUID userId, UUID videoId) {
        History history = historyDao.findByUserIdAndVideoId(userId, videoId);
        if(history == null){
            User user = userDao.findById(userId);
            Video video = videoDao.findById(videoId);
            return historyDao.create(
                    History.builder()
                            .user(user)
                            .video(video)
                            .viewedAt(LocalDateTime.now())
                            .isLiked(Boolean.FALSE)
                            .build()
            );
        }
        return history;
    }

    public boolean updateLike(UUID userId, String href) {
        Video video = videoDao.findByHref(href);
        History history = historyDao.findByUserIdAndVideoId(userId, video.getId());
        if (history.getIsLiked() == Boolean.FALSE){
            history.setIsLiked(Boolean.TRUE);
            history.setLikedAt(LocalDateTime.now());
        }else{
            history.setIsLiked(Boolean.FALSE);
            history.setLikedAt(null);
        }
        History updateHistory = historyDao.update(history);
        return updateHistory.getIsLiked();
    }

    public List<History> findByEmail(String email) {
        return historyDao.findByUserEmail(email);
    }

    public List<VideoDto> findViewedVideoByEmail(String email, int page, int limit) {
        List<History> histories = historyDao.findByUserEmail(email, page, limit);
        return histories.stream()
                .map(History::getVideo)
                .map(videoMapper::toDto)
                .toList();
    }

    public List<VideoDto> findFavoriteVideoByEmail(String email) {
        List<History> histories = historyDao.findByUserEmailAndIsLiked(email);
        return histories.stream()
                .map(History::getVideo)
                .map(videoMapper::toDto)
                .toList();
    }
}
