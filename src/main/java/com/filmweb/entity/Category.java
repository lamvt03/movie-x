package com.filmweb.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "categories")
public class Category {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    @Column(length = 50) @JdbcType(NVarcharJdbcType.class)
    private String name;

    @Column(length = 50, unique = true) @JdbcType(VarcharJdbcType.class)
    private String slug;
    
    @Column(nullable = false, unique = true) @JdbcType(BigIntJdbcType.class)
    private Long ordinal;
    
    /* Audit field */
    @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime updatedAt;
    
    @Column(name = "deleted_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime deletedAt;
    
    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
