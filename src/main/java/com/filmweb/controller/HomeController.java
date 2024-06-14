package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.CommentDto;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Order;
import com.filmweb.service.CommentService;
import com.filmweb.service.HistoryService;
import com.filmweb.service.OrderService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.List;

@ApplicationScoped
@Controller
@Path("/")
public class HomeController {
    @Inject
    private Models models;

    @Inject
    private HttpSession session;

    @Inject
    private VideoService videoService;

    @Inject
    private OrderService orderService;

    @Inject
    private HistoryService historyService;

    @Inject
    private CommentService commentService;

    @GET
    @Path("home")
    public String getHome(
            @QueryParam("page") Integer page
    ) {
        List<VideoDto> trendingVideos = videoService.findTrending(4);
        models.put("trendingVideos", trendingVideos);

        long totalVideo = videoService.count();
        int maxPage = (int) Math.ceil(1.0 * totalVideo / AppConstant.PAGE_LIMIT);
        models.put("maxPage", maxPage);

        int currentPage = 1;
        if (page != null && page <= maxPage) {
            currentPage = page;
        }
        models.put("currentPage", currentPage);

        List<VideoDto> videos = videoService.findAll(currentPage, AppConstant.PAGE_LIMIT);
        models.put("videos", videos);

        List<VideoDto> topVideos = videoService.findTopYear(2024, 1, 4);
        models.put("topVideos", topVideos);

        List<CommentDto> newestComments = commentService.findNewestComments(3);
        models.put("newestComments", newestComments);
        return "home.jsp";
    }

    @GET
    @Path("{category}")
    public String getVideosByCategory(
            @PathParam("category") String categoryCode
    ){
        List<VideoDto> videos = videoService.findByCategoryCode(categoryCode);
        String category = videos.get(0).getCategory();
        models.put("videos",videos);
        models.put("category", category);
        return "video-category.jsp";
    }

    @GET
    @Path("category")
    public String getCategory(
            @QueryParam("page") Integer page
    ){

        long totalVideo = videoService.count();
        int maxPage = (int) Math.ceil(1.0 * totalVideo / AppConstant.CATEGORY_PAGE_LIMIT);
        models.put("maxPage", maxPage);

        int currentPage = 1;
        if (page != null && page <= maxPage) {
            currentPage = page;
        }
        models.put("currentPage", currentPage);

        List<VideoDto> videos = videoService.findAll(currentPage, AppConstant.CATEGORY_PAGE_LIMIT);
        models.put("videos", videos);

        List<VideoDto> topVideos = videoService.findTopYear(2024, 1, 4);
        models.put("topVideos", topVideos);

        List<CommentDto> newestComments = commentService.findNewestComments(3);
        models.put("newestComments", newestComments);

        return "category.jsp";
    }

    @GET
    @Path("search")
    public String getSearch(
            @QueryParam("keyword") String keyword
    ) {
        List<VideoDto> videos = videoService.findByKeyword(keyword);
        models.put("videos", videos);
        return "search.jsp";
    }

    @GET
    @Path("transaction")
    public String getTransaction(

    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if (userDto != null) {
            List<Order> orders = orderService.findByEmail(userDto.email());
            models.put("orders", orders);
        }
        return "transaction.jsp";
    }

    @GET
    @Path("history")
    public String getHistory(
            @QueryParam("page") Integer page
    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            int currentPage = 1;
            if(page != null){
                currentPage = page;
            }
            List<VideoDto> videos = historyService.findViewedVideoByEmail(userDto.email(), currentPage, AppConstant.SEARCH_PAGE_LIMIT);
            models.put("videos", videos);
            models.put("currentPage", currentPage);

            List<History> histories = historyService.findByEmail(userDto.email());
            int maxPage = (int) Math.ceil(1.0 * histories.size() / AppConstant.SEARCH_PAGE_LIMIT);
            models.put("maxPage", maxPage);

        }
        return "history.jsp";
    }
    @GET
    @Path("/favorite")
    public String getFavorite(
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            List<VideoDto> videos = historyService.findFavoriteVideoByEmail(userDto.email());
            models.put("videos", videos);
        }
        return "favorite.jsp";
    }
    @GET
    @Path("/about")
    public String getAbout(
    ){
        return "about.jsp";
    }
}
