package com.filmweb.service;

import static com.filmweb.domain.payment.PaymentStatus.PENDING;

import com.filmweb.dao.PaymentTransactionDao;
import com.filmweb.dao.UserDao;
import com.filmweb.domain.payment.PaymentCardType;
import com.filmweb.domain.payment.PaymentProvider;
import com.filmweb.domain.payment.PaymentStatus;
import com.filmweb.dto.PaymentTransactionDto;
import com.filmweb.entity.PaymentTransaction;
import com.filmweb.utils.PriceFormatUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionService {
  
  @Inject
  private PaymentTransactionDao paymentTransactionDao;
  
  @Inject
  private UserDao userDao;
  
  @Transactional
  public void createNewPaymentTransaction(UUID id, UUID userId, Long amount, PaymentProvider provider) {
    var user = userDao.findById(userId);
    paymentTransactionDao.create(
        PaymentTransaction.builder()
            .id(id)
            .amount(amount)
            .provider(provider)
            .user(user)
            .status(PENDING)
            .build()
    );
  }
  
  @Transactional
  public PaymentTransactionDto updatePaymentTransaction(UUID id, String transactionInfo, String bankCode, String referenceTransactionNumber, String statusCode, PaymentCardType cardType) {
    var paymentTransaction = paymentTransactionDao.findById(id);
    
    if (paymentTransaction == null) {
      throw new RuntimeException("This payment id not issued by Movie X");
    }
    
    paymentTransaction.setTransactionInfo(transactionInfo);
    paymentTransaction.setBankCode(bankCode);
    paymentTransaction.setReferenceTransactionNumber(referenceTransactionNumber);
    paymentTransaction.setStatus(getPaymentStatusByCode(statusCode));
    paymentTransaction.setCardType(cardType);
    paymentTransaction.setPaidAt(LocalDateTime.now());
    
    paymentTransactionDao.update(paymentTransaction);
    
    return toDto(paymentTransactionDao.findById(id));
  }
  
  @Transactional
  public List<PaymentTransactionDto> findLatestPaymentTransaction(int page, int limit) {
    return paymentTransactionDao.findAllOrderByCreatedAtDesc(page, limit)
               .stream()
               .map(this::toDto)
               .toList();
  }
  
  @Transactional
  public List<PaymentTransactionDto> findByStatus(PaymentStatus status) {
    return paymentTransactionDao.findByStatus(status)
               .stream()
               .map(this::toDto)
               .toList();
  }
  
  private PaymentTransactionDto toDto(PaymentTransaction entity) {
    if (entity == null) {
      return null;
    }
    
    return PaymentTransactionDto.builder()
        .id(entity.getId())
        .referenceTransactionNumber(entity.getReferenceTransactionNumber())
        .fullName(entity.getUser().getFullName())
        .paymentAmount(entity.getAmount())
        .formattedPaymentAmount(PriceFormatUtils.toFomattedString(entity.getAmount()))
        .status(entity.getStatus())
        .statusCode(getStatusCode(entity.getStatus()))
        .statusMessage(getStatusMessage(entity.getStatus()))
        .provider(entity.getProvider())
        .cardType(entity.getCardType())
        .createdAt(entity.getCreatedAt())
        .userId(entity.getUser().getId())
        .build();
  }
  
  private PaymentStatus getPaymentStatusByCode(String statusCode) {
    return switch (statusCode) {
      case "00" -> PaymentStatus.SUCCESS;
      default -> PaymentStatus.FAILED;
    };
  }
  
  private String getStatusCode(PaymentStatus status) {
    return switch (status) {
      case SUCCESS -> "success";
      case PENDING -> "warning";
      case FAILED -> "error";
    };
  }
  
  private String getStatusMessage(PaymentStatus status) {
    return switch (status) {
      case SUCCESS -> "Thành công";
      case PENDING -> "Chưa thanh toán";
      case FAILED -> "Thất bại";
    };
  }
}
