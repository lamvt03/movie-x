package com.filmweb.dao;

import com.filmweb.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findAll();

    List<Order> findSuccessfulOrders();

    List<Order> findByUserEmail(String email);

    Order findByUserIdAndVideoId(long userId, long videoId);

    Order create(Order entity);

    Order delete(Order entity);
}
