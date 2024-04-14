package com.filmweb.api;

import com.filmweb.dto.CommentDto;

import java.util.List;

public record CommentListResp(
        List<CommentDto> comments,
        int lastPage
) {
}
