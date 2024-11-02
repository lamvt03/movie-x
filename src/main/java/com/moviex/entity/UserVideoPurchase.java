package com.moviex.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user_video_purchases")
public class UserVideoPurchase {
  
  @Id @GeneratedValue
  @JdbcType(VarcharJdbcType.class)
  private UUID id;
  
  @Column(name = "purchase_amount")
  @JdbcType(BigIntJdbcType.class)
  private Long purchaseAmount;
  
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "video_id", referencedColumnName = "id")
  private Video video;
  
  @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime createdAt;
  
  @PrePersist
  public void onPrePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
