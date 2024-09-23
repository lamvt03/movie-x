package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "videos")
public class Video extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(50)")
    private String title;
    
    @Column(columnDefinition = "nvarchar(50)")
    private String slug;

    @Column(columnDefinition = "varchar(50)", unique = true)
    private String href;

    @Column(columnDefinition = "varchar(255)")
    private String poster;

    private Integer views;

    private Integer share;

    @Column(columnDefinition = "nvarchar(255)")
    private String director;

    @Column(columnDefinition = "nvarchar(255)")
    private String actor;

    @ManyToOne
    @JoinColumn(name = "category_Id", referencedColumnName = "id")
    private Category category;
    
    @Column(columnDefinition = "TEXT")
    private String description;


    private Long price;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<History> histories;

}
