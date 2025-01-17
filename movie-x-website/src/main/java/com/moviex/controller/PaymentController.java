package com.moviex.controller;

import static com.moviex.domain.payment.PaymentProvider.VNPAY;
import static com.moviex.domain.user.UserTransactionType.DEPOSIT;
import static com.moviex.utils.AlertUtils.buildDialogSuccessMessage;
import static com.moviex.utils.AlertUtils.buildToastErrorMessage;

import com.moviex.constant.SessionConstant;
import com.moviex.domain.payment.PaymentCardType;
import com.moviex.dto.UserDto;
import com.moviex.helper.VNPayHelper;
import com.moviex.service.NotificationService;
import com.moviex.service.PaymentTransactionService;
import com.moviex.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@ApplicationScoped
@Controller
@Path("/payment")
public class PaymentController {

    @Inject
    private HttpSession session;

    @Inject
    private VNPayHelper vnpayHelper;
    
    @Inject
    private PaymentTransactionService paymentTransactionService;
    
    @Inject
    private UserService userService;
    
    @Inject
    private NotificationService notificationService;

    @GET
    @Path("/vnpay")
    public Response getPayment(
            @QueryParam("amount") String amount,
            @Context HttpServletRequest servletRequest
    ) {
        Long paymentAmount = null;
        
        try {
            paymentAmount = Long.valueOf(amount);
        } catch (Exception e) {
            buildToastErrorMessage(session, "Số tiền cần nạp phải là một số nguyên dương");
            return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/movie-x/profile")
                .build();
        }
        
        if (paymentAmount < 20000) {
            buildToastErrorMessage(session, "Vui lòng nhập số tiền lớn hơn hoặc bằng 20,000 VND");
            return Response.status(Response.Status.SEE_OTHER)
                       .header(HttpHeaders.LOCATION, "/movie-x/profile")
                       .build();
        }
        
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        String clientIp = servletRequest.getRemoteAddr();
        
        UUID paymentTransactionId = UUID.randomUUID();
        paymentTransactionService.createNewPaymentTransaction(paymentTransactionId, userDto.getId(), paymentAmount, VNPAY);

        String paymentUrl = vnpayHelper.createPaymentUrl(paymentTransactionId, paymentAmount, clientIp);
        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, paymentUrl)
                .build();
    }

    @GET
    @Path("/vnpay/callback")
    public String getSuccessPayment(
            @QueryParam("vnp_TxnRef") UUID paymentTractionId,
            @QueryParam("vnp_OrderInfo") String paymentInfo,
            @QueryParam("vnp_BankCode") String bankCode,
            @QueryParam("vnp_TransactionNo") String referenceTransactionNumber,
            @QueryParam("vnp_TransactionStatus") String statusCode,
            @QueryParam("vnp_CardType") PaymentCardType cardType
    ) {
        var paymentTransaction = paymentTransactionService.updatePaymentTransaction(paymentTractionId, paymentInfo, bankCode, referenceTransactionNumber, statusCode, cardType);
        userService.topUpUserBalance(paymentTransaction.getUserId(), paymentTransaction.getPaymentAmount());
        userService.createNewUserBalanceTransaction(paymentTransaction.getUserId(), paymentTransaction.getPaymentAmount(), DEPOSIT);
        
        var userDto = userService.findById(paymentTransaction.getUserId());
        notificationService.sendAccountDepositEmail(userDto, paymentTransaction.getPaymentAmount(), paymentTransaction.getPaidAt());
        
        buildDialogSuccessMessage(session, "Thành công", "Vui lòng kiểm tra lại số dư tài khoản");
        return "redirect:profile";
    }
}
