package com.filmweb.api;

import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.CommentDto;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.service.CommentService;
import com.filmweb.service.HistoryService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@Path("/api/video")
public class VideoAPI {

    @Inject
    private HttpSession session;

    @Inject
    private HistoryService historyService;

    @Inject
    private CommentService commentService;

    @Inject
    private VideoService videoService;

    @GET
    @Path("like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putLike(
            @QueryParam("v") String href
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        boolean isLiked = historyService.updateLike(userDto.getId(), href);
        Map<String, Boolean> map = Map.of("isLiked", isLiked);
        return Response.status(200).entity(map).build();
    }

    @GET
    @Path("commentList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putLike(
            @QueryParam("v") String href,
            @QueryParam("page") Integer page
    ){
        CommentListResp resp = commentService.loadMoreComments(href, page, 3);
        return Response
                .status(200)
                .entity(resp)
                .build();
    }
}
