package com.filmweb.mapper;

import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Comment;
import com.filmweb.entity.User;
import com.filmweb.util.TimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CommentMapper {
    @Inject
    private TimeFormatter timeFormatter;

    public CommentDto toDto(Comment entity){
        String timeAgo = timeFormatter.getTimeAgoString(entity.getCreatedAt());
        User user = entity.getUser();
        return new CommentDto(
                entity.getContent(),
                timeAgo,
                user.getFullName(),
                user.getAvtId()
        );
    }
}
