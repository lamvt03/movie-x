package com.moviex.controller;

import com.moviex.constant.AppConstant;
import com.moviex.constant.SessionConstant;
import com.moviex.dto.UserDto;
import com.moviex.dto.VideoDto;
import com.moviex.entity.History;
import com.moviex.service.HistoryService;
import com.moviex.service.OrderService;
import com.moviex.service.PaymentTransactionService;
import com.moviex.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.util.List;

@ApplicationScoped
@Controller
@Path("/")
public class UserController {

    @Inject
    private HttpSession session;

    @Inject
    private Models models;

    @Inject
    private UserService userService;

    @Inject
    private OrderService orderService;

    @Inject
    private HistoryService historyService;
    
    @Inject
    private PaymentTransactionService paymentTransactionService;

    @GET
    @Path("/profile")
    public String getProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        var user = userService.findById(userDto.getId());
        models.put("user", user);
        return "user/profile.jsp";
    }
    
    @GET
    @Path("/profile/edit")
    public String getEditProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("user", userDto);
        return "user/edit-profile.jsp";
    }

    @POST
    @Path("/profile/edit")
    public String postEditProfile(
            @FormParam("fullname") String fullname,
            @FormParam("phone") String phone,
            @FormParam("confirmation") Boolean confirm
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if (confirm != null && confirm){
            if (fullname != null && phone != null) {
                UserDto user = userService.editProfile(userDto.getEmail(), fullname, phone);

                if (user != null) {
                    session.removeAttribute(SessionConstant.CURRENT_USER);
                    return "redirect:login";
                }
            }
        }
        return "redirect:home";
    }
    @GET
    @Path("/transaction")
    public String getTransaction(

    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        var paymentTransactions = paymentTransactionService.findByUserId(userDto.getId());
        models.put("paymentTransactions", paymentTransactions);
        return "user/transaction.jsp";
    }

    @GET
    @Path("/history")
    public String getHistory(
            @QueryParam("page") Integer page
    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            int currentPage = 1;
            if(page != null){
                currentPage = page;
            }
            List<VideoDto> videos = historyService.findViewedVideoByEmail(userDto.getEmail(), currentPage, AppConstant.SEARCH_PAGE_LIMIT);
            models.put("videos", videos);
            models.put("currentPage", currentPage);

            List<History> histories = historyService.findByEmail(userDto.getEmail());
            int maxPage = (int) Math.ceil(1.0 * histories.size() / AppConstant.SEARCH_PAGE_LIMIT);
            models.put("maxPage", maxPage);

        }
        return "user/history.jsp";
    }
    @GET
    @Path("/favorite")
    public String getFavorite(
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            List<VideoDto> videos = historyService.findFavoriteVideoByEmail(userDto.getEmail());
            models.put("videos", videos);
        }
        return "user/favorite.jsp";
    }

}



