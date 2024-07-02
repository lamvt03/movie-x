package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.PaymentConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.CommentDto;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.*;
import com.filmweb.service.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.util.List;

@ApplicationScoped
@Path("/video")
public class VideoController {

    @Inject
    private Models models;
    @Inject
    private HttpSession session;

    @Inject
    private VideoService videoService;
    @Inject
    private CommentService commentService;
    @Inject
    private OrderService orderService;
    @Inject
    private RatingService ratingService;

    @Inject
    private HistoryService historyService;

    @Controller
    @GET
    @Path("/watch")
    public String watch(
            @QueryParam("v") String href
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        VideoDto video = videoService.findByHref(href);
        models.put("video", video);

        List<VideoDto> relatedVideos = videoService.findByCategoryCode(video.getCategoryCode(), 1, 3);
        models.put("relatedVideos", relatedVideos);

        List<CommentDto> comments = commentService.findByVideoId(video.getId(), 1, 3);
        models.put("comments", comments);
        int lastPage = commentService.getLastPageByVideoHref(href, 3);
        models.put("lastPage", lastPage);
        if(video.getPrice() > 0){
            if(userDto == null){
               session.setAttribute("buyBeforeWatch", true);
            }else {
                Order order = orderService.findByUserIdAndVideoId(userDto.getId(), video.getId());
                if(order == null || !order.getVnp_ResponseCode().equals(PaymentConstant.VNPAY_SUCCESS_CODE)){
                   session.setAttribute("buyBeforeWatch", true);
                }
            }
        }
        return "user/video-watch.jsp";
    }

    @Controller
    @GET
    @Path("/detail")
    public String getDetail(
            @QueryParam("v") String href
    ){
        VideoDto video = videoService.findByHref(href);
        models.put("video", video);

        List<VideoDto> relatedVideos = videoService.findByCategoryCode(video.getCategoryCode(), 1, 3);
        models.put("relatedVideos", relatedVideos);

        List<CommentDto> comments = commentService.findByVideoId(video.getId(), 1, 3);
        models.put("comments", comments);

        int lastPage = commentService.getLastPageByVideoHref(href, 3);
        models.put("lastPage", lastPage);

        UserDto  userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if(userDto != null){
            Order order  = orderService.findByUserIdAndVideoId(userDto.getId(), video.getId());
            if (order != null) {
                models.put("order", order);
            }

            Rating rating = ratingService.findByUserIdAndVideoId(userDto.getId(), video.getId());
            if (rating != null) {
                int ratingFromDatabase = rating.getStar();
                String checkedAttribute5 = (ratingFromDatabase == 5) ? "checked" : "";
                String checkedAttribute4 = (ratingFromDatabase == 4) ? "checked" : "";
                String checkedAttribute3 = (ratingFromDatabase == 3) ? "checked" : "";
                String checkedAttribute2 = (ratingFromDatabase == 2) ? "checked" : "";
                String checkedAttribute1 = (ratingFromDatabase == 1) ? "checked" : "";

                models.put("checkedAttribute5", checkedAttribute5);
                models.put("checkedAttribute4", checkedAttribute4);
                models.put("checkedAttribute3", checkedAttribute3);
                models.put("checkedAttribute2", checkedAttribute2);
                models.put("checkedAttribute1", checkedAttribute1);
            } else {
                models.put("checkedAttribute5", "");
                models.put("checkedAttribute4", "");
                models.put("checkedAttribute3", "");
                models.put("checkedAttribute2", "");
                models.put("checkedAttribute1", "");
            }
            History history = historyService.create(userDto.getId(), video.getId());
            models.put("flagLikeButton", history.getIsLiked());
        }
        return "user/video-detail.jsp";
    }
}
