package com.filmweb.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user_verified_emails")
public class UserVerifiedEmail {
    
    @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
    private UUID id;
    
    private String token;
    private LocalDateTime expiredAt;
    private Boolean isVerified = Boolean.FALSE;
    private UUID userId;
}
