package com.filmweb.dto;

import com.filmweb.domain.payment.PaymentCardType;
import com.filmweb.domain.payment.PaymentProvider;
import com.filmweb.domain.payment.PaymentStatus;
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
  private String referenceTransactionNumber;
  private UUID userId;
}
