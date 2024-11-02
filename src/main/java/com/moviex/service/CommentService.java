package com.moviex.service;

import com.moviex.api.req.PostCommentReq;
import com.moviex.api.resp.CommentListResp;
import com.moviex.dao.CommentDao;
import com.moviex.dao.UserDao;
import com.moviex.dao.VideoDao;
import com.moviex.dto.CommentDto;
import com.moviex.entity.Comment;
import com.moviex.entity.User;
import com.moviex.entity.Video;
import com.moviex.mapper.CommentMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

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

    
    public List<CommentDto> findByVideoId(UUID videoId, int page, int limit) {
        List<Comment> comments = commentDao.findByVideoId(videoId, page, limit);
        return comments.stream().map(commentMapper::toDto)
                .toList();
    }
    
    public Comment findByUserIdAndVideoId(Long userId, Long videoId) {
        return null;
    }
    
    public Comment create(UUID userId, Long videoId, String content) {
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
    
    public CommentListResp postComment(UUID userId, String href, PostCommentReq req) {
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
