package com.filmweb.controller.admin;

import com.filmweb.constant.AppConstant;
import com.filmweb.dto.VideoDto;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.text.DecimalFormat;
import java.util.List;

@ApplicationScoped
@Controller
@Path("/admin")
public class VideoController {

    @Inject
    private Models models;

    @Inject
    private VideoService videoService;

    @Inject
    private HttpSession session;

    @GET
    @Path("videos")
    public String getVideos(
            @QueryParam("page") Integer page
    ) {
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
        return "admin-video-list.jsp";
    }

    @GET
    @Path("videos/disabled")
    public String getDisabledVideos(
            @QueryParam("page") Integer page
    ) {
        long totalVideo = videoService.countDisabled();
        long maxPage = (long) Math.ceil(1.0 * totalVideo / AppConstant.SEARCH_PAGE_LIMIT);
        models.put("maxPage", maxPage);

        int currentPage = 1;
        if (page != null && page <= maxPage) {
            currentPage = page;
        }
        models.put("currentPage", currentPage);

        List<VideoDto> videos = videoService.findAllDisabled(currentPage, AppConstant.SEARCH_PAGE_LIMIT);
        models.put("videos", videos);
        return "admin-disabled-video-list.jsp";
    }

    @GET
    @Path("video/add")
    public String getVideoAdd() {
        return "admin-video-add.jsp";
    }

    @POST
    @Path("video/add")
    public String postVideoAdd(
            @FormParam("title") String title,
            @FormParam("href") String href,
            @FormParam("poster") String poster,
            @FormParam("director") String director,
            @FormParam("actor") String actor,
            @FormParam("category") String category,
            @FormParam("description") String description,
            @FormParam("price") String formattedPrice,
            @FormParam("content") String content
    ) {
        VideoDto video = videoService.findByHref(href);

        if (video == null) {
            VideoDto videosCreate = videoService.create(title, href, poster, director, actor, category, description, formattedPrice, content);
            if (videosCreate != null) {
                session.setAttribute("addVideoSuccess", true);
                return "redirect:admin/videos";
            }
        } else {
            session.setAttribute("addVideoSuccess", false);
            return "redirect:admin/videos";
        }
        return "admin-video-add.jsp";
    }

    @GET
    @Path("video/edit")
    public String getVideoEdit(
            @QueryParam("v") String href
    ) {
        VideoDto videoDto = videoService.findByHref(href);
        long price = videoDto.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String formattedPrice = decimalFormat.format(price);

        models.put("formattedPrice", formattedPrice);
        models.put("video", videoDto);
        return "admin-video-edit.jsp";
    }
    @POST
    @Path("video/edit")
    public String postVideoEdit(
            @FormParam("title") String title,
            @FormParam("href") String href,
            @FormParam("director") String director,
            @FormParam("actor") String actor,
            @FormParam("category") String category,
            @FormParam("heading") String heading,
            @FormParam("price") String formattedPrice,
            @FormParam("description") String description
    ) {

        VideoDto videoDto = videoService.update(title, href, director, actor, category, heading, formattedPrice, description);
        if(videoDto != null){
            session.setAttribute("updateVideoSuccess", true);
            return "redirect:admin/videos";
        }
        session.setAttribute("updateVideoSuccess", false);
        return "redirect:admin/video/edit";
    }
    @POST
    @Path("video/delete")
    public String postVideoDelete(
            @FormParam("href") String href
    ) {
        VideoDto videoDto = videoService.delete(href);
        if(videoDto != null){
            session.setAttribute("deleteVideoSuccess", true);
        }
        return "redirect:admin/videos";
    }
    @POST
    @Path("video/restore")
    public String postVideoRestore(
            @FormParam("href") String href
    ) {

        VideoDto videoDto = videoService.restore(href);
        if(videoDto != null){
            session.setAttribute("restoreVideoSuccess", true);
        }
        return "redirect:admin/videos/disabled";
    }
}
