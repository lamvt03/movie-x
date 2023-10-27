package com.filmweb.dao.impl;

import com.filmweb.dao.AbstractDao;
import com.filmweb.dao.OrderDao;
import com.filmweb.entity.Order;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

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
        String jpql = "SELECT o FROM Order o WHERE o.user.email = ?0";
        return super.findMany(Order.class, jpql, email);
    }

    @Override
    public Order findByUserIdAndVideoId(long userId, long videoId) {
        String jpql = "SELECT o FROM Order o WHERE o.user.id = ?0 AND o.video.id = ?1";
        return super.findOne(Order.class, jpql, userId, videoId);
    }
}
