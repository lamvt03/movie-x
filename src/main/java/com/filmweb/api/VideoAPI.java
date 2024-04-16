package com.filmweb.api;

import com.filmweb.api.req.PostCommentReq;
import com.filmweb.api.resp.CommentListResp;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.service.CommentService;
import com.filmweb.service.HistoryService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @POST
    @Path("{videoHref}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postComment(
            @PathParam("videoHref") String href,
            PostCommentReq req
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if(userDto != null){
            CommentListResp resp = commentService.postComment(userDto.getId(), href, req);
            return Response.status(200).entity(resp).build();
        }

        return Response
                .status(400)
                .entity(Map.of(
                        "error", 400,
                        "msg", "You must login before post comment"))
                .build();
    }
}
