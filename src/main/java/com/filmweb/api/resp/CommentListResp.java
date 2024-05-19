package com.filmweb.api.resp;

import com.filmweb.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public record CommentListResp(
        List<CommentDto> comments,
        int lastPage
) {
}
