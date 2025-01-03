package com.moviex.entity;

import com.moviex.domain.user.UserRegistrationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

import java.util.List;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.BooleanJdbcType;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "users")
public class User {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    @Column(length = 80) @JdbcType(VarcharJdbcType.class)
    private String password;

    @Column(length = 30) @JdbcType(VarcharJdbcType.class)
    private String email;

    @Column(length = 20) @JdbcType(VarcharJdbcType.class)
    private String phone;
    
    @Column(name = "registration_type", length = 10) @JdbcType(VarcharJdbcType.class)
    @Enumerated(EnumType.STRING)
    private UserRegistrationType registrationType;

    @Column(name = "full_name", length = 50) @JdbcType(NVarcharJdbcType.class)
    private String fullName;

    @Column(name = "is_admin", nullable = false) @JdbcType(BooleanJdbcType.class)
    private Boolean isAdmin = Boolean.FALSE;
    
    @Column(name = "is_fake_user") @JdbcType(BooleanJdbcType.class)
    private Boolean isFakeUser = Boolean.FALSE;
    
    @JdbcType(VarcharJdbcType.class)
    private String image;
    
    @Column(name = "total_balance_amount") @JdbcType(BigIntJdbcType.class)
    private Long totalBalanceAmount;
    
    @Column(name = "remaining_balance_amount")@JdbcType(BigIntJdbcType.class)
    private Long remainingBalanceAmount;
    
    @Column(name = "email_verified_at") @JdbcType(TimestampJdbcType.class)
    private LocalDateTime emailVerifiedAt;

    @OneToMany(mappedBy = "user")
    private List<Share> shares;
    
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
