package com.moviex.dao;

import com.moviex.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderDao {
    List<Order> findAll();

    List<Order> findSuccessfulOrders();

    List<Order> findByUserEmail(String email);

    Order findByUserIdAndVideoId(UUID userId, UUID videoId);

    Order create(Order entity);

    Order delete(Order entity);
}
