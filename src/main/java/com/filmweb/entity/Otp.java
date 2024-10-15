package com.filmweb.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "otps")
public class Otp {
  
  @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
  private UUID id;
  
  @Column(length = 6) @JdbcType(CharJdbcType.class)
  private String code;
  
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  
  @Column(name = "expired_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime expiredAt;
  
  @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime createdAt;
  
  @PrePersist
  public void onPrePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
