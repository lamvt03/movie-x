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

    @Column(columnDefinition = "nvarchar(max)")
    private String title;

    @Column(columnDefinition = "varchar(50)", unique = true)
    private String href;

    @Column(columnDefinition = "varchar(max)")
    private String poster;

    private Integer views;

    private Integer share;

    @Column(columnDefinition = "nvarchar(max)")
    private String director;

    @Column(columnDefinition = "nvarchar(max)")
    private String actor;

    @ManyToOne
    @JoinColumn(name = "category_Id", referencedColumnName = "id")
    private Category category;

    @Column(columnDefinition = "ntext")
    private String description;

    private Long price;

    @Column(name = "is_active")
    private boolean isActive;


//    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
//    private List<Order> orders;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<History> histories;

}
