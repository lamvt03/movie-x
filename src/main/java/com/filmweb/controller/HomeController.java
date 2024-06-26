package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.dto.CommentDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.service.CommentService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
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
    private VideoService videoService;

    @Inject
    private CommentService commentService;

    @GET
    @Path("home")
    public String getHome(
            @QueryParam("page") Integer page
    ) {
        List<VideoDto> trendingVideos = videoService.findTrending(4);
        models.put("trendingVideos", trendingVideos);

        long totalVideo = videoService.countActiveVideos();
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
        List<VideoDto> videos = videoService.findByCategoryCode(categoryCode, 1, 9);
        models.put("videos",videos);

        String category = videos.get(0).getCategory();
        models.put("category", category);

        List<VideoDto> otherVideos = videoService.findOtherVideos(videos.get(0).getCategoryCode(), 1, 4);
        models.put("otherVideos", otherVideos);

        String otherCategory = otherVideos.get(0).getCategory();
        models.put("otherCategory", otherCategory);
        return "video-category.jsp";
    }

    @GET
    @Path("all")
    public String getCategory(
            @QueryParam("page") Integer page
    ){

        long totalVideo = videoService.countActiveVideos();
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

        return "all.jsp";
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
    @Path("/about")
    public String getAbout(
    ){
        return "about.jsp";
    }
}
