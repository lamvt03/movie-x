package com.filmweb.oauth2;

import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.UserDto;
import com.filmweb.service.UserService;
import com.filmweb.utils.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@ApplicationScoped
@Controller
@Path("/oauth2")
public class OAuth2Controller {

    @Inject
    private AppUtils appUtils;

    @Inject
    private HttpSession session;

    @Inject
    private UserService userService;

    @GET
    @Path("/login/google")
    public Response getLoginGoogle(
    ){
        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, GoogleUtils.getLoginUrl())
                .build();
    }
    @GET
    @Path("/login/google/callback")
    public String handleLoginGoogle(
            @QueryParam("code") String code
    ) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String token = GoogleUtils.getToken(code);
        GoogleUser googleUser = GoogleUtils.getUserInfo(token);

        UserDto userDto = Optional.ofNullable(userService.findByEmail(googleUser.email()))
                        .orElseGet(() -> userService.register(googleUser));

        session.setAttribute(SessionConstant.CURRENT_USER, userDto);
        session.setAttribute("loginSuccess", true);

        return "redirect:" + appUtils.getPrevPageUrl(session);
    }


}
