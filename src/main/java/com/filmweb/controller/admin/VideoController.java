package com.filmweb.controller.admin;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.domain.video.VideoCreationPayload;
import com.filmweb.domain.video.VideoUpdatePayload;
import com.filmweb.dto.CategoryDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Video;
import com.filmweb.service.CategoryService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Controller
@Path("/admin")
public class VideoController {

    @Inject
    private Models models;

    @Inject
    private VideoService videoService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private HttpSession session;

    @GET
    @Path("/videos")
    public String getVideos(
            @QueryParam("page") Integer page
    ) {
        long totalVideo = videoService.countActiveVideos();
        long maxPage = (long) Math.ceil(1.0 * totalVideo / AppConstant.SEARCH_PAGE_LIMIT);
        models.put("maxPage", maxPage);

        int currentPage = 1;
        if (page != null && page <= maxPage) {
            currentPage = page;
        }
        models.put("currentPage", currentPage);

        List<VideoDto> videos = videoService.findAll(currentPage, AppConstant.SEARCH_PAGE_LIMIT);
        models.put("videos", videos);
        return "admin/video-list.jsp";
    }

    @GET
    @Path("/videos/disabled")
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

        List<VideoDto> videos = videoService.findDeletedVideos(currentPage, AppConstant.SEARCH_PAGE_LIMIT);
        models.put("videos", videos);
        return "admin/disabled-video-list.jsp";
    }

    @GET
    @Path("/video/add")
    public String getVideoAdd() {
        List<CategoryDto> categories = categoryService.findAll();
        models.put("categories", categories);
        return "admin/video-add.jsp";
    }

    @POST
    @Path("/video/add")
    public String postVideoAdd(
            @FormParam("title") String title,
            @FormParam("href") String href,
            @FormParam("director") String director,
            @FormParam("actor") String actor,
            @FormParam("categoryId") UUID categoryId,
            @FormParam("description") String description,
            @FormParam("price") String formattedPrice
    ) {
        var video = videoService.findByHref(href);

        if (video == null) {
            var v = videoService.createNewVideo(VideoCreationPayload.builder()
                        .title(title)
                        .href(href)
                        .director(director)
                        .actor(actor)
                        .categoryId(categoryId)
                        .description(description)
                        .formattedPrice(formattedPrice)
                        .build());
            if(v != null){
                // TODO: use function
                session.setAttribute("addVideoSuccess", true);
            }
        } else {
            session.setAttribute("addVideoSuccess", false);
        }
        return "redirect:admin/videos";
    }

    @GET
    @Path("/video/edit/{id}")
    public String getVideoEdit(
            @PathParam("id") UUID id
    ) {
        List<CategoryDto> categories = categoryService.findAll();
        models.put("categories", categories);

        VideoDto videoDto = videoService.findById(id);
        long price = videoDto.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String formattedPrice = decimalFormat.format(price);

        models.put("formattedPrice", formattedPrice);
        models.put("video", videoDto);
        return "admin/video-edit.jsp";
    }
    
    @POST
    @Path("/video/edit/{id}")
    public String postVideoEdit(
            @PathParam("id") UUID id,
            @FormParam("title") String title,
            // TODO: can update href because now use id
            @FormParam("href") String href,
            @FormParam("director") String director,
            @FormParam("actor") String actor,
            @FormParam("categorySlug") String categorySlug,
            @FormParam("price") String formattedPrice,
            @FormParam("description") String description
    ) {
        VideoDto videoDto = videoService.updateVideo(
            VideoUpdatePayload.builder()
                .id(id)
                .title(title)
                .director(director)
                .actor(actor)
                .categorySlug(categorySlug)
                .formattedPrice(formattedPrice)
                .description(description)
                .build());
        
        if(videoDto != null){
            session.setAttribute("updateVideoSuccess", true);
            return "redirect:admin/videos";
        }
        session.setAttribute("updateVideoSuccess", false);
        return "redirect:admin/video/edit";
    }
    @POST
    @Path("/video/delete")
    public String postVideoDelete(
            @FormParam("href") String href
    ) {
        VideoDto videoDto = videoService.delete(href);
        if(videoDto != null){
            session.setAttribute("deleteVideoSuccess", true);
        }
        String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
        return "redirect:" + prevUrl;
    }

    @POST
    @Path("/video/restore")
    public String postVideoRestore(
            @FormParam("href") String href
    ) {

        VideoDto videoDto = videoService.restore(href);
        if(videoDto != null){
            session.setAttribute("restoreVideoSuccess", true);
        }
        String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
        return "redirect:" + prevUrl;
    }
}
