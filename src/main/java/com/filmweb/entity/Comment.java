package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
