package com.moviex.entity;

import com.moviex.domain.video.VideoPaymentType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

import java.util.List;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.BooleanJdbcType;
import org.hibernate.type.descriptor.jdbc.LongNVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "videos")
public class Video {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    @Column(length = 50, nullable = false) @JdbcType(NVarcharJdbcType.class)
    private String title;
    
    @Column(length = 50, unique = true, nullable = false) @JdbcType(VarcharJdbcType.class)
    private String slug;
    
    @Column(length = 20, unique = true, nullable = false) @JdbcType(VarcharJdbcType.class)
    private String href;
    
    @Column(unique = true, nullable = false) @JdbcType(VarcharJdbcType.class)
    private String poster;
    
    @Column(name = "payment_type",length = 10) @JdbcType(VarcharJdbcType.class)
    @Enumerated(EnumType.STRING)
    private VideoPaymentType paymentType;

    private Integer views;

    private Integer share;

    @JdbcType(NVarcharJdbcType.class)
    private String director;
    
    @JdbcType(NVarcharJdbcType.class)
    private String actor;
    
    @JdbcType(LongNVarcharJdbcType.class)
    private String description;
    
    @JdbcType(BigIntJdbcType.class)
    private Long price;

    @Column(name = "is_active") @JdbcType(BooleanJdbcType.class)
    private Boolean isActive;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private List<History> histories;

    @ManyToOne @JoinColumn(name = "category_Id", referencedColumnName = "id")
    private Category category;
    
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
