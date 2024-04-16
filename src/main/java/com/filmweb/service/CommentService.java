package com.filmweb.service;

import com.filmweb.api.req.PostCommentReq;
import com.filmweb.api.resp.CommentListResp;
import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;

import java.util.List;

public interface CommentService {

    List<CommentDto> findByVideoId(Long videoId, int page, int limit);

    Comment findByUserIdAndVideoId(Long userId, Long videoId);

    Comment create(Long userId, Long videoId, String content);

    long count();

    CommentListResp loadMoreComments(String href, int page, int limit);

    int getLastPageByVideoHref(String href, int limit);

    CommentListResp postComment(Long userId, String href, PostCommentReq req);
}
