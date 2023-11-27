package com.filmweb.mapper;

import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.util.TimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommentMapper {
    @Inject
    private TimeFormatter timeFormatter;

    public CommentDto toDto(Comment entity){
        String timeAgo = timeFormatter.getTimeAgoString(entity.getCreatedAt());
        String createdBy = entity.getUser().getFullName();
        return new CommentDto(
                entity.getContent(),
                timeAgo,
                createdBy
        );
    }
}
