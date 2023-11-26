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
@Table(name = "_rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int star;

    @Column(columnDefinition = "datetime")
    private Timestamp createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "videoId", referencedColumnName = "id")
    private Video video;
}
