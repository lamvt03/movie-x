package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isLiked;

    @Column(columnDefinition = "datetime")
    private Timestamp viewedAt;

    @Column(columnDefinition = "datetime")
    private Timestamp likedAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "videoId", referencedColumnName = "id")
    private Video video;
}
