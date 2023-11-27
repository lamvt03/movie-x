package com.filmweb.controller;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

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
        List<CommentDto> comments = commentService.findByVideoId(video.getId());
        models.put("comments", comments);
        if(video.getPrice() > 0){
            if(userDto == null){
               session.setAttribute("buyBeforeWatch", true);
            }else {
                Order order = orderService.findByUserIdAndVideoId(userDto.getId(), video.getId());
                if(order == null || !order.getVnp_ResponseCode().equals("00")){
                   session.setAttribute("buyBeforeWatch", true);
                }
            }
        }
        return "video-watch.jsp";
    }

    @Controller
    @GET
    @Path("/detail")
    public String getDetail(
            @QueryParam("v") String href
    ){
        VideoDto video = videoService.findByHref(href);
        models.put("video", video);

        List<CommentDto> comments = commentService.findByVideoId(video.getId());
        models.put("comments", comments);

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
        return "video-detail.jsp";
    }

    @Controller
    @POST
    @Path("/comment")
    public String postComment(
            @FormParam("content") String content,
            @FormParam("href") String videoHref
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if(userDto != null) {
            VideoDto video = videoService.findByHref(videoHref);
            commentService.create(userDto.getId(), video.getId(), content);
        }
        return "redirect:video/detail?v="+videoHref;
    }
    @PUT
    @Path("like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putLike(
            @QueryParam("v") String href
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        boolean isLiked = historyService.updateLike(userDto.getId(), href);
        Map<String, Boolean> map = Map.of("isLiked", isLiked);
        return Response.status(200).entity(map).build();
    }
}
