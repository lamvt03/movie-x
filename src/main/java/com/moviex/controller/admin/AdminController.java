package com.moviex.controller.admin;

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
public class AdminController {

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

        if (admin != null) {
            Boolean isAdmin = admin.getIsAdmin();
            Boolean isActive = admin.getIsActive();

            if (isAdmin && isActive) {
                session.setAttribute(SessionConstant.CURRENT_ADMIN, admin);
                session.setAttribute("loginAdmin", true);
                return "redirect:admin/dashboard";
            } else {
                session.setAttribute("loginAdminFail", true);
                return "redirect:admin";
            }
        } else {
            session.setAttribute("loginAdmin", false);
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
