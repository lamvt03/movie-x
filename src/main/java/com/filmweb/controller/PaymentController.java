package com.filmweb.controller;

import com.filmweb.constant.PaymentConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Order;
import com.filmweb.helper.VNPayHelper;
import com.filmweb.service.OrderService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import java.text.ParseException;

@ApplicationScoped
@Controller
@Path("/payment")
public class PaymentController {

    @Inject
    private OrderService orderService;

    @Inject
    private VideoService videoService;

    @Inject
    private HttpSession session;

    @Inject
    private VNPayHelper vnpayHelper;

    @GET
    @Path("/vnpay")
    public Response getPayment(
            @QueryParam("href") String href,
            @Context HttpServletRequest servletRequest
    ) {
        String clientIp = servletRequest.getRemoteAddr();

        VideoDto video = videoService.findByHref(href);
        long amount = video.getPrice() * 100;

        String paymentUrl = vnpayHelper.createPaymentUrl(clientIp, href, amount);
        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, paymentUrl)
                .build();
    }

    @GET
    @Path("/vnpay/callback")
    public String getSuccessPayment(
            @QueryParam("href") String href,
            @QueryParam("vnp_TxnRef") String vnp_TxnRef,
            @QueryParam("vnp_OrderInfo") String vnp_OrderInfo,
            @QueryParam("vnp_PayDate") String vnp_PayDate,
            @QueryParam("vnp_ResponseCode") String vnp_ResponseCode,
            @QueryParam("vnp_Amount") Long vnp_Amount,
            @QueryParam("vnp_BankCode") String vnp_BankCode,
            @QueryParam("vnp_TransactionNo") String vnp_TransactionNo
    ) throws ParseException {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        Order order = orderService.create(userDto.getId(), href, vnp_TxnRef, vnp_OrderInfo, vnp_PayDate, vnp_ResponseCode, vnp_Amount, vnp_BankCode, vnp_TransactionNo);
        if(order.getVnp_ResponseCode().equals(PaymentConstant.VNPAY_SUCCESS_CODE)){
            session.setAttribute("paySuccess", true);
        } else {
            session.setAttribute("paySuccess", false);
        }

        // TODO: update
        VideoDto video = videoService.findByHref(href);
        return "redirect:v/detail/" + video.getSlug();
    }
}
