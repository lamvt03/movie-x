package com.moviex.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "histories")
public class History {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;

    @Column(name = "is_liked")
    // @JdbcType(BooleanJdbcType.class)
    private Boolean isLiked;

    @Column(name = "viewed_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime viewedAt;

    @Column(name = "liked_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime likedAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Video video;
}
