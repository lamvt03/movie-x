package com.filmweb.mapper;

import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Video;
import com.filmweb.utils.TimeFormatter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class VideoMapper {
    @Inject
    private TimeFormatter timeFormatter;

    public VideoDto toDto(Video entity) {
        if(entity != null){
            List<History> histories = entity.getHistories().stream()
                    .filter(History::getIsLiked)
                    .toList();
            String timeAgo = timeFormatter.getTimeAgoString(entity.getCreatedAt());
            return new VideoDto(
                    entity.getId(),
                    entity.getTitle(),
                    entity.getHref(),
                    entity.getPoster(),
                    entity.getViews(),
                    entity.getShare(),
                    entity.getDirector(),
                    entity.getActor(),
                    entity.getCategory().getName(),
                    entity.getCategory().getCode(),
                    entity.getDescription(),
                    entity.getPrice(),
                    entity.isActive(),
                    entity.getCreatedAt(),
                    histories.size(),
                    entity.getComments().size(),
                    timeAgo
            );
        }
        return null;
    }
}
