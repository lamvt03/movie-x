package com.filmweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentDto{
    private String content;
    private String timeAgo;
    private String createdBy;
}
