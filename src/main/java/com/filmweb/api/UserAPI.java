package com.filmweb.api;

import com.filmweb.api.req.CheckUserBalanceReq;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.service.UserVideoPurchaseService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@ApplicationScoped
@Path("/api/user")
public class UserAPI {
  
  @Inject
  private HttpSession session;
  
  @Inject
  private UserVideoPurchaseService userVideoPurchaseService;
  
  @POST
  @Path("/check-balance")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response postComment(
      CheckUserBalanceReq req
  ){
    UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
    
    if(userDto != null) {
      var resp = userVideoPurchaseService.checkUserBalanceToPurchaseVideo(userDto.getId(), req.videoId());
      return Response.status(200)
                 .entity(resp).build();
    }
    
    return Response
      .status(400)
      .entity(Map.of(
         "error", 400,
         "msg", "You must login before check balance"))
      .build();
  }
}
