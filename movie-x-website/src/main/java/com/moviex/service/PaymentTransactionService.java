package com.moviex.service;

import static com.moviex.domain.payment.PaymentStatus.PENDING;

import com.moviex.dao.PaymentTransactionDao;
import com.moviex.dao.UserDao;
import com.moviex.domain.payment.PaymentCardType;
import com.moviex.domain.payment.PaymentProvider;
import com.moviex.domain.payment.PaymentStatus;
import com.moviex.dto.PaymentTransactionDto;
import com.moviex.entity.PaymentTransaction;
import com.moviex.exception.MovieXException;
import com.moviex.utils.PriceFormatUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionService {
  
  @Inject
  private PaymentTransactionDao paymentTransactionDao;
  
  @Inject
  private UserDao userDao;
  
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
  
  public PaymentTransactionDto updatePaymentTransaction(UUID id, String transactionInfo, String bankCode, String referenceTransactionNumber, String statusCode, PaymentCardType cardType) {
    var paymentTransaction = paymentTransactionDao.findById(id);
    
    if (paymentTransaction == null) {
      throw new MovieXException("This payment id not issued by Movie X");
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
  
  public List<PaymentTransactionDto> findLatestPaymentTransaction(int page, int limit) {
    return paymentTransactionDao.findAllOrderByCreatedAtDesc(page, limit)
               .stream()
               .map(this::toDto)
               .toList();
  }
  
  public List<PaymentTransactionDto> findByStatus(PaymentStatus status) {
    return paymentTransactionDao.findByStatus(status)
               .stream()
               .map(this::toDto)
               .toList();
  }
  
  public List<PaymentTransactionDto> findByUserId(UUID userId) {
    return paymentTransactionDao.findByUserIdOrderByCreatedAtDesc(userId)
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
        .paidAt(entity.getPaidAt())
        .build();
  }
  
  private PaymentStatus getPaymentStatusByCode(String statusCode) {
    // Only for VNPay
    if (statusCode.equals("00")) {
      return PaymentStatus.SUCCESS;
    }
    return PaymentStatus.FAILED;
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
