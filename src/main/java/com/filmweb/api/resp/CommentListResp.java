package com.filmweb.api.resp;

import com.filmweb.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentListResp {
    private List<CommentDto> comments;
    private int lastPage;
}
