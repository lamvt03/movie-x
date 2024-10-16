package com.filmweb.controller;

import static com.filmweb.utils.AlertUtils.buildToastErrorMessage;
import static com.filmweb.utils.AlertUtils.buildToastSuccessMessage;

import com.filmweb.constant.CookieConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.domain.user.UserRegistrationType;
import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.UserDto;
import com.filmweb.helper.GoogleOauthHelper;
import com.filmweb.service.UserService;
import com.filmweb.service.JwtService;
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
            ) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String token = googleOauthHelper.getToken(code);
        GoogleUser googleUser = googleOauthHelper.getUserInfo(token);

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
