package com.filmweb.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "shares")
public class Share {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String email;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;
}
