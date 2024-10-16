package com.filmweb.entity;

import com.filmweb.domain.payment.PaymentCardType;
import com.filmweb.domain.payment.PaymentProvider;
import com.filmweb.domain.payment.PaymentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Entity @Table(name = "payment_transactions")
public class PaymentTransaction {
  
  @Id @JdbcType(VarcharJdbcType.class)
  private UUID id;
  
  @JdbcType(BigIntJdbcType.class)
  private Long amount;
  
  @Column(length = 20) @JdbcType(VarcharJdbcType.class)
  @Enumerated(EnumType.STRING)
  private PaymentProvider provider;
  
  @Column(length = 20) @JdbcType(VarcharJdbcType.class)
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
  
  @Column(name = "reference_transaction_number",length = 50) @JdbcType(VarcharJdbcType.class)
  private String referenceTransactionNumber;
  
  @Column(name = "transaction_info",length = 100) @JdbcType(VarcharJdbcType.class)
  private String transactionInfo;
  
  @Column(name = "bank_code",length = 10) @JdbcType(VarcharJdbcType.class)
  private String bankCode;
  
  @Column(name = "card_type",length = 10) @JdbcType(VarcharJdbcType.class)
  @Enumerated(EnumType.STRING)
  private PaymentCardType cardType;
  
  @Column(name = "paid_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime paidAt;
  
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  
  @Column(name = "created_at") @JdbcType(TimestampJdbcType.class)
  private LocalDateTime createdAt;
  
  @PrePersist
  public void onPrePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
