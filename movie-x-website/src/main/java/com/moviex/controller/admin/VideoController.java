package com.moviex.controller.admin;

import static com.moviex.utils.AlertUtils.prepareDialogErrorMessage;
import static com.moviex.utils.AlertUtils.prepareToastErrorMessage;
import static com.moviex.utils.AlertUtils.prepareToastSuccessMessage;

import com.moviex.constant.AppConstant;
import com.moviex.constant.SessionConstant;
import com.moviex.domain.video.payload.VideoCreationPayload;
import com.moviex.domain.video.payload.VideoUpdatePayload;
import com.moviex.dto.CategoryDto;
import com.moviex.dto.VideoDto;
import com.moviex.service.CategoryService;
import com.moviex.service.VideoService;
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
                prepareToastSuccessMessage(session, "Thêm phim thành công");
            }
        } else {
            prepareDialogErrorMessage(session, "Thất Bại", "Video đã tồn tại trong cơ sở dữ liệu");
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
                .href(href)
                .categorySlug(categorySlug)
                .formattedPrice(formattedPrice)
                .description(description)
                .build());
        
        if(videoDto != null){
            prepareToastSuccessMessage(session, "Chỉnh sửa phim thành công");
            return "redirect:admin/videos";
        }
        prepareToastErrorMessage(session, "Chỉnh sửa phim thất bại");
        return "redirect:admin/video/edit";
    }
    @POST
    @Path("/video/delete")
    public String postVideoDelete(
            @FormParam("href") String href
    ) {
        VideoDto videoDto = videoService.delete(href);
        if(videoDto != null){
            prepareToastSuccessMessage(session, "Xoá video thành công");
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
            prepareToastSuccessMessage(session, "Khôi phục video thành công");
        }
        String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
        return "redirect:" + prevUrl;
    }
}
