package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(50)")
    private String name;

    @Column(columnDefinition = "nvarchar(50)", unique = true)
    private String code;

    @Column(columnDefinition = "datetime")
    private Timestamp createdAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = (this.createdAt == null) ? new Timestamp(System.currentTimeMillis()) : this.createdAt;
    }
}
