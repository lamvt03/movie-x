package com.filmweb.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "comments")
public class Comment {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    @Column(length = 200) @JdbcType(NVarcharJdbcType.class)
    private String content;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;
    
    /* Audit fields */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
