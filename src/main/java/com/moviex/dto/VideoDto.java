package com.moviex.dto;

import com.moviex.domain.video.VideoPaymentType;
import java.util.UUID;
import lombok.Builder;

import java.time.LocalDateTime;
import lombok.Value;

@Value
@Builder
public class VideoDto {
    UUID id;
    String title;
    String slug;
    String href;
    String poster;
    VideoPaymentType paymentType;
    Integer views;
    Integer share;
    String director;
    String actor;
    String category;
    String categorySlug;
    String description;
    Long price;
    String formattedPrice;
    Boolean isActive;
    LocalDateTime createdAt;
    Integer likeQuantity;
    Integer commentQuantity;
    String timeAgo;
}
