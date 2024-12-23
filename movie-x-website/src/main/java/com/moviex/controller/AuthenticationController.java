package com.moviex.controller;

import static com.moviex.constant.SessionConstant.CURRENT_USER;
import static com.moviex.utils.AlertUtils.buildDialogSuccessMessage;
import static com.moviex.utils.AlertUtils.buildToastErrorMessage;

import com.moviex.constant.SessionConstant;
import com.moviex.domain.user.InternalRegistrationPayload;
import com.moviex.dto.UserDto;
import com.moviex.service.OtpService;
import com.moviex.service.UserService;
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
import lombok.extern.jbosslog.JBossLog;

@JBossLog
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
    log.infof("RECEIVED call to login account with email [%s]", email);
    
    return userService.handleLogin(session, response, email, password);
  }

  @GET
  @Path("/logout")
  public String getLogout(
      @Context HttpServletRequest request,
      @Context HttpServletResponse response
  ) {
    userService.logoutUser(session, request, response);

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
    return userService.handleInternalRegistration(
        session, InternalRegistrationPayload.builder()
                .email(email)
                .phone(phone)
                .password(password)
                .fullName(fullName)
                .build());
  }

  @GET
  @Path("/verify")
  public String verify(@QueryParam("token") String token) {
    return userService.handleVerifyOnboardingToken(token, session);
  }

  @GET
  @Path("/verify/error")
  public String verifyExpired(){return "user/verify-error.jsp";}

  @GET
  @Path("/verify/resend")
  public String resendVerificationEmail() {
    return userService.handleResendVerificationEmail(session);
  }

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
    
    buildToastErrorMessage(session, "Mã OTP không chính xác hoặc đã hết hạn");
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
  ) {
    String email = session.getAttribute("email").toString();
    if(email != null && password != null){
      UserDto userDto = userService.changePassword(email, password.trim());

      if (userDto != null) {
        buildDialogSuccessMessage(session, "Thành công", "Lấy lại mật khẩu thành công");
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
  public String getChangePassword() {
    UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
    models.put("user", userDto);
    return "user/change-password.jsp";
  }

  @POST
  @Path("/password/change")
  public String postChangePassword(
      @Context HttpServletRequest request,
      @Context HttpServletResponse response,
      @FormParam("oldPass") String oldPassword,
      @FormParam("newPass") String newPassword
  ){
    UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
    return userService.handleChangePassword(session, request, response, models, userDto, oldPassword, newPassword);
  }
  
  @GET
  @Path("/account/delete")
  public String deleteMyAccount(
      @Context HttpServletRequest request,
      @Context HttpServletResponse response
  ){
    UserDto userDto = (UserDto) session.getAttribute(CURRENT_USER);
    return userService.handleDeleteMyAccount(session, request, response, userDto);
  }
}
