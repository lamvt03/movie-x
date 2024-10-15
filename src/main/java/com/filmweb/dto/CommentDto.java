package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
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
