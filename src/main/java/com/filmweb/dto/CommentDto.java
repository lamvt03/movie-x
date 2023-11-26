package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentDto{
    private String content;
    private String timeAgo;
    private String createdBy;
}
