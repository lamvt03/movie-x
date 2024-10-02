package com.filmweb.dto;

import com.filmweb.entity.Comment;
import com.filmweb.entity.Order;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
@Builder
public class VideoDto {
    UUID id;
    String title;
    String slug;
    String href;
    String poster;
    Integer views;
    Integer share;
    String director;
    String actor;
    String category;
    String categorySlug;
    String description;
    Long price;
    Boolean isActive;
    LocalDateTime createdAt;
    Integer likeQuantity;
    Integer commentQuantity;
    String timeAgo;
}
