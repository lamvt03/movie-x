package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "histories")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_liked")
    private Boolean isLiked;

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @Column(name = "liked_at")
    private LocalDateTime likedAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;
}
