package com.moviex.api.resp;

import com.moviex.dto.CommentDto;

import java.util.List;

public record CommentListResp(
        List<CommentDto> comments,
        int lastPage
) {
}
