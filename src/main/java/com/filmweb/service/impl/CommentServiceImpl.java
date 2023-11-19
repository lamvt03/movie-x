package com.filmweb.service.impl;

import com.filmweb.dao.CommentDao;
import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.service.CommentService;
import com.filmweb.util.TimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Inject
    private TimeFormatter timeFormatter;

    @Override
    public List<Comment> findByUser(String username) {
        return null;
    }

    @Override
    public List<CommentDto> findByVideoId(Long videoId) {
        List<Comment> comments = commentDao.findByVideoId(videoId);
        return comments.stream().map(comment -> {
            String timeAgo = timeFormatter.getTimeAgoString(comment.getCreatedAt());
            String createdBy = comment.getUser().getFullName();
            return new CommentDto(
                    comment.getContent(),
                    timeAgo,
                    createdBy
            );
        }).toList();
    }

    @Override
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
        return null;
    }

    @Override
    public Comment findByVideoIdGetUser(Long videoId) {
        return null;
    }

    @Override
    public Comment create(User user, Video video, String content) {
        return null;
    }
}
