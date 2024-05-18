package com.filmweb.service.impl;

import com.filmweb.api.req.PostCommentReq;
import com.filmweb.api.resp.CommentListResp;
import com.filmweb.dao.CommentDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.mapper.CommentMapper;
import com.filmweb.service.CommentService;
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
    public List<CommentDto> findByVideoId(Long videoId, int page, int limit) {
        List<Comment> comments = commentDao.findByVideoId(videoId, page, limit);
        return comments.stream().map(commentMapper::toDto)
                .toList();
    }

    @Override
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
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

    @Override
    public long count() {
        return commentDao.count();
    }

    @Override
    public CommentListResp loadMoreComments(String href, int page, int limit) {
        List<CommentDto> comments = commentDao.findManyByVideoHref(href, page, limit)
                .stream()
                .map(commentMapper::toDto)
                .toList();
        int lastPage = this.getLastPageByVideoHref(href, limit);
        return new CommentListResp(comments, lastPage);
    }

    @Override
    public int getLastPageByVideoHref(String href, int limit) {
        long totalComments = commentDao.countByVideoHref(href);
        return (int)Math.ceil(1.0 * totalComments / limit);
    }

    @Override
    public CommentListResp postComment(Long userId, String href, PostCommentReq req) {
        User user = userDao.findById(userId);
        Video video = videoDao.findByHref(href);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setVideo(video);
        comment.setContent(req.content());
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentDao.create(comment);

        return this.loadMoreComments(href,1, 3);
    }

    @Override
    public List<CommentDto> findNewestComments(int limit) {
        return commentDao.findAllOrderByDesc(1, limit)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
