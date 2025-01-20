package com.moviex.controller.admin;

import static com.moviex.utils.AlertUtils.prepareToastErrorMessage;
import static com.moviex.utils.AlertUtils.prepareToastSuccessMessage;

import com.moviex.constant.SessionConstant;
import com.moviex.dto.UserDto;
import com.moviex.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;

@ApplicationScoped
@Controller
@Path("/admin")
public class AuthenticationController {

    @Inject
    private UserService userService;

    @Inject
    private HttpSession session;

    @GET
    @Path("/")
    public String getLogin(){
        return "admin/login.jsp";
    }

    @POST
    @Path("/")
    public String postLogin(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        UserDto admin = userService.authenticate(username, password);

        if (admin != null && admin.getIsAdmin() && admin.getIsActive()) {
            session.setAttribute(SessionConstant.CURRENT_ADMIN, admin);
            prepareToastSuccessMessage(session, "Đăng nhập thành công");
            return "redirect:admin/dashboard";
        } else {
            prepareToastErrorMessage(session, "Sai thông tin đăng nhập");
            return "redirect:admin";
        }
    }
    
    @GET
    @Path("/logout")
    public String getLogout(){
        session.removeAttribute(SessionConstant.CURRENT_ADMIN);
        return "redirect:admin";
    }
}
