package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private String title;

    private String href;

    private String poster;

    private Integer views;

    private Integer shares;

    private String heading;

    private String director;

    private String actors;

    private String category;

    private String description;

    private int price;

    private Boolean isActive;

    private LocalDateTime created_at;

    @OneToMany(mappedBy = "video")
    private List<Order> orders;
}
