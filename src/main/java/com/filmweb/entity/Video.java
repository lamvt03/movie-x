package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "_video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(max)")
    private String title;

    @Column(columnDefinition = "varchar(50)")
    private String href;

    @Column(columnDefinition = "varchar(max)")
    private String poster;

    private Integer views;

    private Integer share;

    @Column(columnDefinition = "ntext")
    private String heading;

    @Column(columnDefinition = "nvarchar(max)")
    private String director;

    @Column(columnDefinition = "nvarchar(max)")
    private String actor;

    @Column(columnDefinition = "nvarchar(max)")
    private String category;

    @Column(columnDefinition = "ntext")
    private String description;

    private Long price;

    private boolean isActive;

    @Column(columnDefinition = "datetime")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "video")
    private List<Order> orders;

    @OneToMany(mappedBy = "video" , fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    private List<History> histories;
}
