package com.filmweb.controller;

import com.filmweb.constant.SessionConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.UserVerifiedEmail;
import com.filmweb.service.EmailService;
import com.filmweb.service.UserService;
import com.filmweb.util.AppUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

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

    @Inject
    private UserVerifiedEmailDao verifiedEmailDao;

    @Inject
    private AppUtils appUtils;

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
            boolean isAdmin = userDto.isAdmin();
            boolean isActive = userDto.isActive();

            if (!isAdmin && isActive) {
                session.setAttribute("loginSuccess", true);
                session.setAttribute(SessionConstant.CURRENT_USER, userDto);

                String prevUrl = appUtils.getPrevPageUrl(session);

                return "redirect:" + prevUrl;
            } else {
                session.setAttribute("emailNotVerified", true);
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
        String prevUrl = appUtils.getPrevPageUrl(session);
        return "redirect:" + prevUrl;
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
        UserVerifiedEmail verifiedEmail = verifiedEmailDao.findByToken(token);
        if(verifiedEmail.getIsVerified()){
            session.setAttribute("alreadyVerified", true);
            return "redirect:login";
        }
        else if(verifiedEmail.getExpiredAt().isAfter(LocalDateTime.now())){
            verifiedEmail.setIsVerified(Boolean.TRUE);
            verifiedEmailDao.update(verifiedEmail);
            UserDto user = userService.findById(verifiedEmail.getUserId());
            session.setAttribute("email", user.email());
            return "redirect:verify/success";
        }else{

        }
//        UserDto user = userService.verifyEmail(token);
//        session.setAttribute("email", user.getEmail());
        return "redirect:home";
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
            if (userDto.isActive()) {
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
        models.put("email", userDto.email());
        models.put("phone", userDto.phone());
        models.put("fullName", userDto.fullName());
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
        if(userService.comparePassword(userDto.email(), oldPassword)){
            if (confirm != null && confirm) {
                UserDto user = userService.changePassword(userDto.email(), newPassword.trim());

                if (user != null) {
                    session.removeAttribute(SessionConstant.CURRENT_USER);
                    session.setAttribute("newPassSuccess", true);
                    return "redirect:login";
                }
            }
        }
        models.put("email", userDto.email());
        models.put("phone", userDto.phone());
        models.put("fullName", userDto.fullName());
        session.setAttribute("oldPasswordWrong", true);
        return "change-password.jsp";
    }


    @GET
    @Path("profile")
    public String getProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("user", userDto);
        return "profile.jsp";
    }
    @GET
    @Path("profile/edit")
    public String getEditProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("user", userDto);
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
                UserDto user = userService.editProfile(userDto.email(), fullname, phone);

                if (user != null) {
                    session.removeAttribute(SessionConstant.CURRENT_USER);
                    return "redirect:login";
                }
            }
        }
        return "redirect:home";
    }

}



