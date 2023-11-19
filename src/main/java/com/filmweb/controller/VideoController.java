package com.filmweb.controller;

import com.filmweb.dto.CommentDto;
import com.filmweb.entity.Video;
import com.filmweb.service.CommentService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.ws.rs.*;

import java.util.List;

@ApplicationScoped
@Controller
@Path("/video")
public class VideoController {

    @Inject
    private Models models;
    @Inject
    private VideoService videoService;

    @Inject
    CommentService commentService;

    @GET
    @Path("/watch")
    public String watch(
            @QueryParam("v") String href
    ){
        Video video = videoService.findByHref(href);
        models.put("video", video);
        List<CommentDto> comments = commentService.findByVideoId(video.getId());
        models.put("comments", comments);
        return "video-watch.jsp";
    }

    @GET
    @Path("/detail")
    public String getDetail(
            @QueryParam("v") String href
    ){
        Video video = videoService.findByHref(href);
        models.put("video", video);

        return "video-detail.jsp";
    }
}
