package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Order;
import com.filmweb.entity.User;
import com.filmweb.entity.Video;
import com.filmweb.mapper.VideoMapper;
import com.filmweb.service.HistoryService;
import com.filmweb.service.OrderService;
import com.filmweb.service.VideoService;
import com.filmweb.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.Objects;

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


    @GET
    @Path("home")
    public String getHome(
            @QueryParam("page") Integer page
//            @QueryParam("limit") Integer limit
    ) {
        List<Video> trendingVideos = videoService.findTrending(4);
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
        return "home.jsp";
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
            List<Order> orders = orderService.findByEmail(userDto.getEmail());
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
            List<VideoDto> videos = historyService.findViewedVideoByEmail(userDto.getEmail(), currentPage, AppConstant.SEARCH_PAGE_LIMIT);
            models.put("videos", videos);
            models.put("currentPage", currentPage);

            List<History> histories = historyService.findByEmail(userDto.getEmail());
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
            List<VideoDto> videos = historyService.findFavoriteVideoByEmail(userDto.getEmail());
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
