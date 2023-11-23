package com.filmweb.controller;

import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.service.EmailService;
import com.filmweb.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;

@ApplicationScoped
@Controller
@Path("/")
public class UserController {

    @Inject
    private HttpSession session;

    @Context
    private ServletContext servletContext;

    @Inject
    private Models models;

    @Inject
    private UserService userService;
    @Inject
    private EmailService emailService;

    @GET
    @Path("login")
    public String getLogin(){
        return "login.jsp";
    }

    @POST
    @Path("login")
    public String postLogin(
            @FormParam("email") String email,
            @FormParam("password") String password
    ){
        UserDto userDto = userService.authenticate(email, password);
        if (userDto != null) {
            boolean isAdmin = userDto.getIsAdmin();
            boolean isActive = userDto.getIsActive();

            if (!isAdmin && isActive) {
                session.setAttribute("loginSuccess", true);
                session.setAttribute(SessionConstant.CURRENT_USER, userDto);
                return "redirect:home";
            } else {
                session.setAttribute("loginFail", true);
                return "login.jsp";
            }
        } else {
            session.setAttribute("loginSuccess", false);
            return "login.jsp";
        }
    }
    @GET
    @Path("logout")
    public String getLogout(){
        session.removeAttribute(SessionConstant.CURRENT_USER);
        return "redirect:home";
    }

    @GET
    @Path("register")
    public String getRegister(){
        return "register.jsp";
    }

    @POST
    @Path("register")
    public String postRegister(
            @FormParam("email") String email,
            @FormParam("phone") String phone,
            @FormParam("password") String password,
            @FormParam("fullName") String fullName
            ) throws MessagingException {
        boolean existedEmail = userService.existByEmail(email);
        boolean existedPhone = userService.existsByPhone(phone);

        if (!existedEmail && !existedPhone) {
            UserDto auth = userService.register(email, password, phone, fullName);

            if (auth != null) {
                emailService.sendRegisterEmail(servletContext, auth);
                session.setAttribute("registerSuccess", true);
                return "redirect:login";
            }
        } else {
            session.setAttribute("existPhone", existedPhone);
            session.setAttribute("existEmail", existedEmail);
        }
        return "redirect:register";
    }
    @GET
    @Path("verify")
    public String verify(
            @QueryParam("token") String token
    ){
        UserDto user = userService.verifyEmail(token);
        session.setAttribute("email", user.getEmail());
        return "redirect:verify/success";
    }

    @GET
    @Path("verify/success")
    public String verifySuccess(){
        return "verify-success.jsp";
    }

    @GET
    @Path("otp/enter")
    public String enterOtp(){
        return "enter-otp.jsp";
    }

    @POST
    @Path("otp/validate")
    public String validateOtp(
            @FormParam("otp") String otp
    ){
        String sysOtp = (String)session.getAttribute("otp");
        if(otp.trim().equals(sysOtp)){
            return "redirect:password/new";
        }
        session.setAttribute("errorOTP", true);
        return "redirect:otp/enter";
    }

    @GET
    @Path("password/new")
    public String getNewPassword(){
        return "new-password.jsp";
    }

    @POST
    @Path("password/new")
    public String postNewPassword(
            @FormParam("password") String password
    ){
        String email = (String) session.getAttribute("email");
        if(email != null && password != null){
            UserDto userDto = userService.changePassword(email, password.trim());

            if (userDto != null) {
                session.setAttribute("changePassSuccess", true);
            }
        }
        return "redirect:login";
    }

    @GET
    @Path("password/forgot")
    public String getForgot(){
        return "forgot-password.jsp";
    }

    @POST
    @Path("password/forgot")
    public String postForgot(
            @FormParam("email") String email
    ) throws MessagingException {
        UserDto userDto = userService.findByEmail(email);
        if (userDto != null){
            if (userDto.getIsActive()) {
                userService.sendForgotPasswordMessage(servletContext, session, userDto);
                return "redirect:otp/enter";
            } else {
                session.setAttribute("userFalse", true);
            }
        }else{
            session.setAttribute("existEmail", true);
        }
        return "redirect:password/forgot";
    }

    @GET
    @Path("password/change")
    public String getChangePassword(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("email", userDto.getEmail());
        models.put("phone", userDto.getPhone());
        models.put("fullName", userDto.getFullName());
        return "change-password.jsp";
    }

    @POST
    @Path("password/change")
    public String postChangePassword(
            @FormParam("oldPass") String oldPassword,
            @FormParam("newPass") String newPassword,
            @FormParam("confirmation") Boolean confirm
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if(userService.comparePassword(userDto.getEmail(), oldPassword)){
            if (confirm != null && confirm) {
                UserDto user = userService.changePassword(userDto.getEmail(), newPassword);

                if (user != null) {
                    session.removeAttribute(SessionConstant.CURRENT_USER);
                    session.setAttribute("newPassSuccess", true);
                    return "redirect:login";
                }
            }
        }
        models.put("email", userDto.getEmail());
        models.put("phone", userDto.getPhone());
        models.put("fullName", userDto.getFullName());
        session.setAttribute("oldPasswordWrong", true);
        return "change-password.jsp";
    }


    @GET
    @Path("profile")
    public String getProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("email", userDto.getEmail());
        models.put("phone", userDto.getPhone());
        models.put("fullName", userDto.getFullName());
        return "profile.jsp";
    }
    @GET
    @Path("profile/edit")
    public String getEditProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("email", userDto.getEmail());
        models.put("phone", userDto.getPhone());
        models.put("fullName", userDto.getFullName());
        return "edit-profile.jsp";
    }

    @POST
    @Path("profile/edit")
    public String postEditProfile(
            @FormParam("fullname") String fullname,
            @FormParam("phone") String phone,
            @FormParam("confirmation") Boolean confirm
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if (confirm != null && confirm){
            if (fullname != null && phone != null) {
                UserDto user = userService.editProfile(userDto.getEmail(), fullname, phone);

                if (user != null) {
                    session.removeAttribute(SessionConstant.CURRENT_USER);
                    return "redirect:login";
                }
            }
        }
        return "redirect:home";
    }


}



