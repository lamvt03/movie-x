package com.filmweb.service;

import com.filmweb.entity.Order;

import java.text.ParseException;
import java.util.List;

public interface OrderService {

    Order findByUserIdAndVideoId(Long userId, Long videoId);
    List<Order> findByEmail(String email);
    Order create(Long userId, String videoHref,  String vnp_TxnRef, String vnp_OrderInfo, String vnp_PayDate, String vnp_ResponseCode, Long vnp_Amount, String vnp_BankCode, String vnp_TransactionNo) throws ParseException;
    List<Order> findAll();
    List<Order> findSuccessfulOrders();
}
