package com.moviex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto{
    private String content;
    private String timeAgo;
    private String createdBy;
    private String userImage;
}
