package com.filmweb.controller.admin;

import com.filmweb.entity.Order;
import com.filmweb.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@ApplicationScoped
@Controller
@Path("/admin")
public class HomeController {

    @Inject
    private Models models;

    @Inject
    private OrderService orderService;

    @GET
    @Path("dashboard")
    public String getDashboard(){
        List<Order> orders = orderService.findAll();
        List<Order> successfulOrders = orderService.findSuccessfulOrders();

        long totalPrice = successfulOrders.stream()
                .map(Order::getVnp_Amount)
                .reduce((long)0, Long::sum);

        models.put("totalPrice", totalPrice);
        models.put("orders", orders);
        return "admin-dashboard.jsp";
    }
}
