package com.moviex.dao;

import com.moviex.domain.payment.PaymentStatus;
import com.moviex.entity.PaymentTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionDao extends AbstractDao<PaymentTransaction> {
  
  public PaymentTransaction findById(UUID id) {
    return super.findById(PaymentTransaction.class, id);
  }
  
  public List<PaymentTransaction> findAllOrderByCreatedAtDesc(int page, int limit) {
    String jpql = "SELECT o FROM PaymentTransaction o ORDER BY o.createdAt DESC";
    return super.findMany(PaymentTransaction.class, page, limit, jpql);
  }
  
  public List<PaymentTransaction> findByStatus(PaymentStatus status) {
    String jpql = "SELECT o FROM PaymentTransaction o WHERE o.status = ?1 ";
    return super.findMany(PaymentTransaction.class, jpql, status);
  }
  
  public List<PaymentTransaction> findByUserIdOrderByCreatedAtDesc(UUID userId) {
    String jpql = "SELECT o FROM PaymentTransaction o JOIN o.user u WHERE u.id = ?1 ORDER BY o.createdAt DESC";
    return super.findMany(PaymentTransaction.class, jpql, userId);
  }
}
