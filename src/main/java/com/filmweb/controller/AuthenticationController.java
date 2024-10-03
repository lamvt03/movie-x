package com.filmweb.controller;

import static com.filmweb.constant.SessionConstant.CURRENT_USER;

import com.filmweb.constant.CookieConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dao.UserVerifiedEmailDao;
import com.filmweb.dto.UserDto;
import com.filmweb.service.JwtService;
import com.filmweb.service.MailSenderService;
import com.filmweb.service.OtpService;
import com.filmweb.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import java.util.Arrays;

@ApplicationScoped
@Controller
@Path("/")
public class AuthenticationController {

  @Inject
  private HttpSession session;

  @Inject
  private UserService userService;

  @Inject
  private Models models;

  @Inject
  private MailSenderService mailSenderService;

  @Inject
  private UserVerifiedEmailDao verifiedEmailDao;

  @Inject
  private JwtService jwtService;
  
  @Inject
  private OtpService otpService;

  @GET
  @Path("/login")
  public String getLogin(){ return "user/login.jsp"; }

  @GET
  @Path("/register")
  public String getRegister(){
    return "user/register.jsp";
  }

  @POST
  @Path("/login")
  public String postLogin(
      @FormParam("email") String email,
      @FormParam("password") String password,
      @Context HttpServletResponse response
  ){
    
    return userService.handleLogin(session, response, email, password);
  }

  @GET
  @Path("/logout")
  public String getLogout(
      @Context HttpServletRequest request,
      @Context HttpServletResponse response
  ){
    session.removeAttribute(CURRENT_USER);

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
  @Path("/register")
  public String postRegister(
      @FormParam("email") String email,
      @FormParam("phone") String phone,
      @FormParam("password") String password,
      @FormParam("fullName") String fullName
  ) {
    boolean existedEmail = userService.existByEmail(email);
    boolean existedPhone = userService.existsByPhone(phone);

    if (!existedEmail && !existedPhone) {
      UserDto auth = userService.register(email, password, phone, fullName);
      if (auth != null) {
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
  @Path("/verify")
  public String verify(@QueryParam("token") String token) {
    return userService.handleVerifyOnboardingToken(token, session);
  }

  @GET
  @Path("/verify/error")
  public String verifyExpired(){return "user/verify-error.jsp";}

  // @GET
  // @Path("/verify/resend")
  // public String resendVerifiedEmail() throws MessagingException, UnsupportedEncodingException {
  //   String verifiedEmail = session.getAttribute(SessionConstant.VERIFIED_EMAIL).toString();
  //   UserDto auth = userService.findByEmail(verifiedEmail);
  //   mailSenderService.sendRegisterEmail(auth);
  //   session.removeAttribute(SessionConstant.VERIFIED_EMAIL);
  //   return "redirect:verify/notify";
  // }

  @GET
  @Path("/verify/notify")
  public String notifyVerifiedEmail(){
    return "user/verify-notify.jsp";
  }

  @GET
  @Path("/verify/success")
  public String verifySuccess(){
    return "user/verify-success.jsp";
  }

  @GET
  @Path("/otp/enter")
  public String enterOtp(){
    return "user/enter-otp.jsp";
  }

  @POST
  @Path("/otp/validate")
  public String validateOtp(
      @FormParam("otp") String otpCode
  ){
    if(otpService.validateOtpCode(otpCode.trim())) {
      return "redirect:password/new";
    }
    session.setAttribute("errorOTP", true);
    return "redirect:otp/enter";
  }

  @GET
  @Path("/password/new")
  public String getNewPassword(){
    return "user/new-password.jsp";
  }

  @POST
  @Path("/password/new")
  public String postNewPassword(
      @FormParam("password") String password
  ){
    String email = session.getAttribute("email").toString();
    if(email != null && password != null){
      UserDto userDto = userService.changePassword(email, password.trim());

      if (userDto != null) {
        session.setAttribute("changePassSuccess", true);
        session.removeAttribute("email");
      }
    }
    return "redirect:login";
  }

  @GET
  @Path("/password/forgot")
  public String getForgot(){
    return "user/forgot-password.jsp";
  }

  @POST
  @Path("/password/forgot")
  public String postForgot(@FormParam("email") String email) {
    return userService.handleForgotPassword(email, session);
  }

  @GET
  @Path("/password/change")
  public String getChangePassword(){
    UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
    models.put("email", userDto.getEmail());
    models.put("phone", userDto.getPhone());
    models.put("fullName", userDto.getFullName());
    return "user/change-password.jsp";
  }

  @POST
  @Path("/password/change")
  public String postChangePassword(
      @FormParam("oldPass") String oldPassword,
      @FormParam("newPass") String newPassword,
      @FormParam("confirmation") Boolean confirm
  ){
    UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
    if(userService.comparePassword(userDto.getEmail(), oldPassword)){
      if (confirm != null && confirm) {
        UserDto user = userService.changePassword(userDto.getEmail(), newPassword.trim());

        if (user != null) {
          session.removeAttribute(CURRENT_USER);
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
}
