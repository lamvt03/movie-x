package com.filmweb.service.impl;

import com.filmweb.dao.CommentDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.CommentDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.mapper.CommentMapper;
import com.filmweb.service.CommentService;
import com.filmweb.util.TimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Timestamp;
import java.util.List;

@ApplicationScoped
public class CommentServiceImpl implements CommentService {

    @Inject
    private CommentDao commentDao;

    @Inject
    private CommentMapper commentMapper;

    @Inject
    private UserDao userDao;

    @Inject
    private VideoDao videoDao;


    @Override
    public List<Comment> findByUser(String username) {
        return null;
    }

    @Override
    public List<CommentDto> findByVideoId(Long videoId) {
        List<Comment> comments = commentDao.findByVideoId(videoId);
        return comments.stream().map(commentMapper::toDto)
                .toList();
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
    public Comment create(Long userId, Long videoId, String content) {
        User user = userDao.findById(userId);
        Video video = videoDao.findById(videoId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setVideo(video);
        comment.setContent(content);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return commentDao.create(comment);
    }
}
