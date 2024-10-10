package com.filmweb.entity;

import com.filmweb.domain.user.UserTransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.descriptor.jdbc.TimestampJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "user_balance_transactions")
public class UserBalanceTransaction {
  
  @Id @GeneratedValue @JdbcType(VarcharJdbcType.class)
  private UUID id;
  
  @JdbcType(BigIntJdbcType.class)
  private Long amount;
  
  @Column(name = "transaction_type", length = 20) @JdbcType(VarcharJdbcType.class)
  @Enumerated(EnumType.STRING)
  private UserTransactionType transactionType;
  
  @OneToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  
  @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime createdAt;
  
  @PrePersist
  public void onPrePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
