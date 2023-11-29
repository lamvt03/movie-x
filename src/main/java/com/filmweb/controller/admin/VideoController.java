package com.filmweb.controller.admin;

import com.filmweb.constant.AppConstant;
import com.filmweb.dto.VideoDto;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.List;

@ApplicationScoped
@Controller
@Path("/admin")
public class VideoController {

    @Inject
    private Models models;

    @Inject
    private VideoService videoService;

    @GET
    @Path("videos")
    public String getVideos(
            @QueryParam("page") Integer page
    ){
        long totalVideo = videoService.count();
        long maxPage = (long) Math.ceil(1.0 * totalVideo / AppConstant.SEARCH_PAGE_LIMIT);
        models.put("maxPage", maxPage);

        int currentPage = 1;
        if (page != null && page <= maxPage) {
            currentPage = page;
        }
        models.put("currentPage", currentPage);

        List<VideoDto> videos = videoService.findAll(currentPage, AppConstant.SEARCH_PAGE_LIMIT);
        models.put("videos", videos);
        return "admin-create-video.jsp";
    }

}
