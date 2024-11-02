package com.moviex.dao.impl;

import com.moviex.dao.AbstractDao;
import com.moviex.dao.OrderDao;
import com.moviex.entity.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {

    @Override
    public List<Order> findAll() {
        String jpql = "SELECT o FROM Order o ORDER BY o.vnp_PayDate DESC";
        return super.findMany(Order.class, 5, jpql);
    }

    @Override
    public List<Order> findSuccessfulOrders() {
        String jpql = "SELECT o FROM Order o WHERE o.vnp_ResponseCode = '00' ORDER BY o.vnp_PayDate DESC";
        return super.findMany(Order.class, jpql);
    }

    @Override
    public List<Order> findByUserEmail(String email) {
        String jpql = "SELECT o FROM Order o WHERE o.user.email = ?1";
        return super.findMany(Order.class, jpql, email);
    }

    @Override
    public Order findByUserIdAndVideoId(UUID userId, UUID videoId) {
        String jpql = "SELECT o FROM Order o WHERE o.user.id = ?1 AND o.video.id = ?2";
        return super.findOne(Order.class, jpql, userId, videoId);
    }
}
