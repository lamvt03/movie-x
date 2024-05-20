package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(50)")
    private String name;

    @Column(columnDefinition = "nvarchar(50)", unique = true)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
