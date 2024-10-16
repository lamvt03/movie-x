package com.filmweb.dao;

import com.filmweb.domain.payment.PaymentStatus;
import com.filmweb.entity.PaymentTransaction;
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
}
