package com.moviex.controller;

import static com.moviex.utils.AlertUtils.buildToastErrorMessage;
import static com.moviex.utils.AlertUtils.buildToastSuccessMessage;

import com.moviex.constant.CookieConstant;
import com.moviex.constant.SessionConstant;
import com.moviex.domain.user.UserRegistrationType;
import com.moviex.dto.GoogleUser;
import com.moviex.dto.UserDto;
import com.moviex.helper.GoogleOauthHelper;
import com.moviex.service.UserService;
import com.moviex.service.JwtService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Controller
@Path("/oauth2")
public class OAuth2Controller {

    @Inject
    private HttpSession session;

    @Inject
    private UserService userService;

    @Inject
    private GoogleOauthHelper googleOauthHelper;

    @Inject
    private JwtService jwtService;

    @GET
    @Path("/login/google")
    public Response getLoginGoogle(
    ) {
        String loginUrl = googleOauthHelper.createLoginUrl();
        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, loginUrl)
                .build();
    }
    
    @GET
    @Path("/login/google/callback")
    public String handleLoginGoogle(
            @QueryParam("code") String code,
            @Context HttpServletResponse response
            ) {
        GoogleUser googleUser = googleOauthHelper.getUserInfo(code);

        UserDto userDto = userService.findByEmailAndRegistrationType(googleUser.getEmail(), UserRegistrationType.INTERNAL);
        
        if (userDto != null) {
            buildToastErrorMessage(session, "Email này đã được đăng ký tài khoản trước đó");
            return "redirect:login";
        }
        
        var userRegistered = userService.register(googleUser);

        session.setAttribute(SessionConstant.CURRENT_USER, userRegistered);
        
        String rememberToken = jwtService.generateRememberToken(userRegistered);
        Cookie loginCookie = new Cookie(CookieConstant.REMEMBER_TOKEN, rememberToken);
        loginCookie.setMaxAge(CookieConstant.LOGIN_DURATION);
        response.addCookie(loginCookie);
      
        buildToastSuccessMessage(session, "Đăng nhập thành công");
        String prevPageUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
        return "redirect:" + prevPageUrl;
    }
}
