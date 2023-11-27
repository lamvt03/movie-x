package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;
import com.filmweb.service.VideoService;
import com.filmweb.util.JPAUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
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

    @GET
    @Path("home")
    public String index(
            @QueryParam("page") Integer page
//            @QueryParam("limit") Integer limit
    ){
        List<Video> trendingVideos = videoService.findTrending(4);
        models.put("trendingVideos", trendingVideos);

        long totalVideo = videoService.count();
        int maxPage = (int)Math.ceil(1.0 * totalVideo / AppConstant.PAGE_LIMIT);
        models.put("maxPage", maxPage);

        List<VideoDto> videos;
        if(page == null || page > maxPage){
            videos = videoService.findAll(1, AppConstant.PAGE_LIMIT);
            models.put("currentPage", 1);
        }else{
            videos = videoService.findAll(page, AppConstant.PAGE_LIMIT);
            models.put("currentPage", page);
        }
        models.put("videos", videos);
        return "home.jsp";
    }
}
