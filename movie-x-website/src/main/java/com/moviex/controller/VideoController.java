package com.moviex.controller;

import static com.moviex.utils.AlertUtils.prepareDialogWarningMessage;

import com.moviex.constant.SessionConstant;
import com.moviex.dto.CommentDto;
import com.moviex.dto.UserDto;
import com.moviex.dto.VideoDto;
import com.moviex.entity.*;
import com.moviex.service.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.util.List;

@ApplicationScoped
@Path("/v")
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
    private RatingService ratingService;
    @Inject
    private HistoryService historyService;
    @Inject
    private UserVideoPurchaseService userVideoPurchaseService;
    @Inject
    private UserService userService;

    @Controller
    @GET
    @Path("/watch/{slug}")
    public String watch(
            @PathParam("slug") String slug
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        VideoDto video = videoService.findBySlug(slug);
        models.put("video", video);

        List<VideoDto> relatedVideos = videoService.findByCategorySlug(video.getCategorySlug(), 1, 3);
        models.put("relatedVideos", relatedVideos);

        List<CommentDto> comments = commentService.findByVideoId(video.getId(), 1, 3);
        models.put("comments", comments);
        
        int lastPage = commentService.getLastPageByVideoHref(video.getHref(), 3);
        models.put("lastPage", lastPage);
        
        if(video.getPrice() > 0){
            if(userDto == null || !userVideoPurchaseService.checkUserVideoPurchase(userDto.getId(), video.getId())) {
               prepareDialogWarningMessage(session, "Cảnh báo", "Bạn chưa mua phim này!");
               return "redirect:v/detail/" + slug;
            }
        }
        
        return "user/video-watch.jsp";
    }

    @Controller
    @GET
    @Path("/detail/{slug}")
    public String getDetail(
            @PathParam("slug") String slug
    ){
        VideoDto video = videoService.findBySlug(slug);
        models.put("video", video);

        List<VideoDto> relatedVideos = videoService.findByCategorySlug(video.getCategorySlug(), 1, 3);
        models.put("relatedVideos", relatedVideos);

        List<CommentDto> comments = commentService.findByVideoId(video.getId(), 1, 3);
        models.put("comments", comments);
        
        int lastPage = commentService.getLastPageByVideoHref(slug, 3);
        models.put("lastPage", lastPage);

        UserDto  userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if(userDto != null){
            var wasPurchasedByUser = userVideoPurchaseService.checkUserVideoPurchase(userDto.getId(), video.getId());
            models.put("wasPurchasedByUser", wasPurchasedByUser);

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
            History history = historyService.createNewHistory(userDto.getId(), video.getId());
            models.put("flagLikeButton", history.getIsLiked());
        }
        return "user/video-detail.jsp";
    }
    
    @Controller
    @GET
    @Path("/purchase/{slug}")
    public String purchaseVideo(
        @PathParam("slug") String slug
    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        return userService.handlePurchaseVideo(session, userDto.getId(), slug);
    }
}
