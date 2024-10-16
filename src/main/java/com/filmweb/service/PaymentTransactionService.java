package com.filmweb.service;

import static com.filmweb.domain.payment.PaymentStatus.PENDING;

import com.filmweb.dao.PaymentTransactionDao;
import com.filmweb.dao.UserDao;
import com.filmweb.domain.payment.PaymentCardType;
import com.filmweb.domain.payment.PaymentProvider;
import com.filmweb.domain.payment.PaymentStatus;
import com.filmweb.dto.PaymentTransactionDto;
import com.filmweb.entity.PaymentTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
  
  private PaymentTransactionDto toDto(PaymentTransaction entity) {
    if (entity == null) {
      return null;
    }
    
    return PaymentTransactionDto.builder()
        .id(entity.getId())
        .paymentAmount(entity.getAmount())
        .userId(entity.getUser().getId())
        .build();
  }
  
  private PaymentStatus getPaymentStatusByCode(String statusCode) {
    return switch (statusCode) {
      case "00" -> PaymentStatus.SUCCESS;
      default -> PaymentStatus.FAILED;
    };
  }
}