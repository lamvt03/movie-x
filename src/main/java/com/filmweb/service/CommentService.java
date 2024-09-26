package com.filmweb.service;

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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CommentService {

    @Inject
    private CommentDao commentDao;

    @Inject
    private CommentMapper commentMapper;

    @Inject
    private UserDao userDao;

    @Inject
    private VideoDao videoDao;

    
    public List<CommentDto> findByVideoId(Long videoId, int page, int limit) {
        List<Comment> comments = commentDao.findByVideoId(videoId, page, limit);
        return comments.stream().map(commentMapper::toDto)
                .toList();
    }
    
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
        return null;
    }
    
    public Comment create(Long userId, Long videoId, String content) {
        User user = userDao.findById(userId);
        Video video = videoDao.findById(videoId);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setVideo(video);
        comment.setContent(content);
        return commentDao.create(comment);
    }
    
    public long count() {
        return commentDao.count();
    }
    
    public CommentListResp loadMoreComments(String href, int page, int limit) {
        List<CommentDto> comments = commentDao.findManyByVideoHref(href, page, limit)
                .stream()
                .map(commentMapper::toDto)
                .toList();
        int lastPage = this.getLastPageByVideoHref(href, limit);
        return new CommentListResp(comments, lastPage);
    }
    
    public int getLastPageByVideoHref(String href, int limit) {
        long totalComments = commentDao.countByVideoHref(href);
        return (int)Math.ceil(1.0 * totalComments / limit);
    }
    
    public CommentListResp postComment(Long userId, String href, PostCommentReq req) {
        User user = userDao.findById(userId);
        Video video = videoDao.findByHref(href);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setVideo(video);
        comment.setContent(req.content());
        commentDao.create(comment);

        return this.loadMoreComments(href,1, 3);
    }
    
    public List<CommentDto> findNewestComments(int limit) {
        return commentDao.findAllOrderByDesc(1, limit)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
