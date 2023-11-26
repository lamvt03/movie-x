package com.filmweb.service.impl;

import com.filmweb.dao.OrderDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dao.VideoDao;
import com.filmweb.entity.Order;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;

    @Inject
    private UserDao userDao;

    @Inject
    private VideoDao videoDao;

    @Override
    public Order findByUserIdAndVideoId(Long userId, Long videoId) {
        return orderDao.findByUserIdAndVideoId(userId, videoId);
    }

    @Override
    public Order create(Long userId, String videoHref, String vnp_TxnRef, String vnp_OrderInfo, String vnp_PayDate, String vnp_ResponseCode, Long vnp_Amount, String vnp_BankCode, String vnp_TransactionNo) throws ParseException {
        User user = userDao.findById(userId);
        Video video = videoDao.findByHref(videoHref);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = sdf.parse(vnp_PayDate);
        Timestamp payDate = new Timestamp(date.getTime());

        Long price = vnp_Amount/100;

        Order order = Order.builder()
                .user(user)
                .video(video)
                .vnp_TxnRef(vnp_TxnRef)
                .vnp_OrderInfo(vnp_OrderInfo)
                .vnp_PayDate(payDate)
                .vnp_ResponseCode(vnp_ResponseCode)
                .vnp_BankCode(vnp_BankCode)
                .vnp_TransactionNo(vnp_TransactionNo)
                .vnp_Amount(price)
                .build();

        return orderDao.create(order);
    }
}
