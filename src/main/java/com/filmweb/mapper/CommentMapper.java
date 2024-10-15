package com.filmweb.mapper;

import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.utils.TimeFormatUtils;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentMapper {

    public CommentDto toDto(Comment entity){
        
        if (entity == null) {
            return null;
        }
        
        return CommentDto.builder()
            .content(entity.getContent())
            .timeAgo(TimeFormatUtils.getTimeAgoString(entity.getCreatedAt()))
            .createdBy(entity.getUser().getFullName())
            .userImage(entity.getUser().getImage())
            .build();
    }
}
