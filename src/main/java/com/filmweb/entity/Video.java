package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "_video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "ntext")
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

    private int price;

    private boolean isActive;

    @Column(columnDefinition = "datetime")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "video")
    private List<Order> orders;

    @OneToMany(mappedBy = "video")
    private List<Comment> comments;

    @OneToMany(mappedBy = "video")
    private List<History> histories;
}
