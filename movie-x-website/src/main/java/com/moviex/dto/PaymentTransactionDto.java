package com.moviex.dto;

import com.moviex.domain.payment.PaymentCardType;
import com.moviex.domain.payment.PaymentProvider;
import com.moviex.domain.payment.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentTransactionDto {
  
  private UUID id;
  private String fullName;
  private Long paymentAmount;
  private String formattedPaymentAmount;
  private PaymentStatus status;
  private String statusCode;
  private String statusMessage;
  private PaymentProvider provider;
  private PaymentCardType cardType;
  private LocalDateTime createdAt;
  private String formattedCreatedAt;
  private String referenceTransactionNumber;
  private UUID userId;
  private LocalDateTime paidAt;
}
