package com.filmweb.dao;

import com.filmweb.entity.PaymentTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionDao extends AbstractDao<PaymentTransaction> {
  
  public PaymentTransaction findById(UUID id) {
    return super.findById(PaymentTransaction.class, id);
  }
}
