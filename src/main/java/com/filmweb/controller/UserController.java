package com.filmweb.controller;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.CookieConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.History;
import com.filmweb.entity.Order;
import com.filmweb.entity.UserVerifiedEmail;
import com.filmweb.service.MailService;
import com.filmweb.service.HistoryService;
import com.filmweb.service.OrderService;
import com.filmweb.service.UserService;
import com.filmweb.utils.JwtUtils;
import com.filmweb.utils.MailUtils;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@Controller
@Path("/")
public class UserController {

    @Inject
    private HttpSession session;

    @Inject
    private Models models;

    @Inject
    private UserService userService;
    @Inject
    private MailService mailService;

    @Inject
    private OrderService orderService;

    @Inject
    private HistoryService historyService;

    @Inject
    private UserVerifiedEmailDao verifiedEmailDao;

    @Inject
    private JwtUtils jwtUtils;

    @Inject
    private RandomUtils randomUtils;


    @POST
    @Path("login")
    public String postLogin(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @Context HttpServletResponse response
    ){
        UserDto userDto = userService.authenticate(email, password);
        if (userDto != null) {
            boolean isAdmin = userDto.getIsAdmin();
            boolean isActive = userDto.getIsActive();

            if (!isAdmin && isActive) {
                session.setAttribute("loginSuccess", true);
                session.setAttribute(SessionConstant.CURRENT_USER, userDto);

                String rememberToken = jwtUtils.generateRememberToken(userDto);
                Cookie loginCookie = new Cookie(CookieConstant.REMEMBER_TOKEN, rememberToken);
                loginCookie.setMaxAge(CookieConstant.LOGIN_DURATION);
                response.addCookie(loginCookie);

                String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
                return "redirect:" + prevUrl;
            } else {
                session.setAttribute("emailNotVerified", true);
                return "user/login.jsp";
            }
        } else {
            session.setAttribute("loginSuccess", false);
            return "user/login.jsp";
        }
    }

    @GET
    @Path("logout")
    public String getLogout(
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
            ){
        session.removeAttribute(SessionConstant.CURRENT_USER);

        if(request.getCookies() != null){
            Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(CookieConstant.REMEMBER_TOKEN))
                    .findFirst()
                    .ifPresent(cookie -> {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    });
        }

        String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
        return "redirect:" + prevUrl;
    }

    @POST
    @Path("register")
    public String postRegister(
            @FormParam("email") String email,
            @FormParam("phone") String phone,
            @FormParam("password") String password,
            @FormParam("fullName") String fullName
            ) throws MessagingException, UnsupportedEncodingException {
        boolean existedEmail = userService.existByEmail(email);
        boolean existedPhone = userService.existsByPhone(phone);

        if (!existedEmail && !existedPhone) {
            UserDto auth = userService.register(email, password, phone, fullName);

            if (auth != null) {
                mailService.sendRegisterEmail(auth);
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
            UserDto user = userService.verify(verifiedEmail.getUserId());
            session.setAttribute("email", user.getEmail());
            return "redirect:verify/success";
        }else{
            UserDto user = userService.findById(verifiedEmail.getUserId());
            session.setAttribute(SessionConstant.VERIFIED_EMAIL, user.getEmail());
            return "redirect:verify/expired";
        }
    }

    @GET
    @Path("verify/expired")
    public String verifyExpired(){return "user/verify-expired.jsp";}

    @GET
    @Path("verify/resend")
    public String resendVerifiedEmail() throws MessagingException, UnsupportedEncodingException {
        String verifiedEmail = session.getAttribute(SessionConstant.VERIFIED_EMAIL).toString();
        UserDto auth = userService.findByEmail(verifiedEmail);
        mailService.sendRegisterEmail(auth);
        session.removeAttribute(SessionConstant.VERIFIED_EMAIL);
        return "redirect:verify/notify";
    }

    @GET
    @Path("verify/notify")
    public String notifyVerifiedEmail(){
        return "user/verify-notify.jsp";
    }

    @GET
    @Path("verify/success")
    public String verifySuccess(){
        return "user/verify-success.jsp";
    }

    @GET
    @Path("otp/enter")
    public String enterOtp(){
        return "user/enter-otp.jsp";
    }

    @POST
    @Path("otp/validate")
    public String validateOtp(
            @FormParam("otp") String otp
    ){
        String sysOtp = session.getAttribute("otp").toString();
        if(otp.trim().equals(sysOtp)){
            return "redirect:password/new";
        }
        session.setAttribute("errorOTP", true);
        return "redirect:otp/enter";
    }

    @GET
    @Path("password/new")
    public String getNewPassword(){
        return "user/new-password.jsp";
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
        return "user/forgot-password.jsp";
    }

    @POST
    @Path("password/forgot")
    public String postForgot(
            @FormParam("email") String email
    ) throws MessagingException, UnsupportedEncodingException {
        UserDto userDto = userService.findByEmail(email);
        if (userDto != null){
            if (userDto.getIsActive()) {
                String otp = randomUtils.randomOtpValue(AppConstant.OTP_LENGTH);
                mailService.sendForgotEmail(userDto, otp);
                session.setAttribute("otp", otp);
                session.setMaxInactiveInterval(300);
                session.setAttribute("email", userDto.getEmail());
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
        return "user/change-password.jsp";
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
                UserDto user = userService.changePassword(userDto.getEmail(), newPassword.trim());

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
        models.put("user", userDto);
        return "user/profile.jsp";
    }
    @GET
    @Path("profile/edit")
    public String getEditProfile(){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        models.put("user", userDto);
        return "user/edit-profile.jsp";
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
    @GET
    @Path("transaction")
    public String getTransaction(

    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        if (userDto != null) {
            List<Order> orders = orderService.findByEmail(userDto.getEmail());
            models.put("orders", orders);
        }
        return "user/transaction.jsp";
    }

    @GET
    @Path("history")
    public String getHistory(
            @QueryParam("page") Integer page
    ) {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            int currentPage = 1;
            if(page != null){
                currentPage = page;
            }
            List<VideoDto> videos = historyService.findViewedVideoByEmail(userDto.getEmail(), currentPage, AppConstant.SEARCH_PAGE_LIMIT);
            models.put("videos", videos);
            models.put("currentPage", currentPage);

            List<History> histories = historyService.findByEmail(userDto.getEmail());
            int maxPage = (int) Math.ceil(1.0 * histories.size() / AppConstant.SEARCH_PAGE_LIMIT);
            models.put("maxPage", maxPage);

        }
        return "user/history.jsp";
    }
    @GET
    @Path("favorite")
    public String getFavorite(
    ){
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);

        if (userDto != null) {
            List<VideoDto> videos = historyService.findFavoriteVideoByEmail(userDto.getEmail());
            models.put("videos", videos);
        }
        return "user/favorite.jsp";
    }

}



