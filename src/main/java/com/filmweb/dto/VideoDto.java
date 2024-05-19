package com.filmweb.dto;

import com.filmweb.entity.Comment;
import com.filmweb.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VideoDto {
    private Long id;

    private String title;

    private String href;

    private String poster;

    private Integer views;

    private Integer share;

    private String director;

    private String actor;

    private String category;
    private String categoryCode;

    private String description;

    private Long price;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private Integer likeQuantity;

    private Integer commentQuantity;

    private String timeAgo;
}
