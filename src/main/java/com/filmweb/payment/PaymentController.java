package com.filmweb.payment;

import com.filmweb.constant.PaymentConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dto.UserDto;
import com.filmweb.dto.VideoDto;
import com.filmweb.entity.Order;
import com.filmweb.service.OrderService;
import com.filmweb.service.VideoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
@Controller
@Path("/payment")
public class PaymentController {

    @Inject
    private OrderService orderService;

    @Inject
    private VideoService videoService;

    @Inject
    private HttpSession session;

    @Inject
    private HttpServletRequest servletRequest;

    @GET
    @Path("/vnpay")
    public Response getPayment(
            @QueryParam("href") String href
    ) {
        String clientIp = servletRequest.getRemoteAddr();

        VideoDto video = videoService.findByHref(href);
        long amount = video.getPrice() * 100;
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);

        String returnUrl = VNPayConfig.vnp_ReturnUrl + "?href=" + href;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", VNPayConfig.vnp_TestingBankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", VNPayConfig.vnp_OrderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", clientIp);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);


        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, paymentUrl)
                .build();
    }

    @GET
    @Path("/vnpay/callback")
    public String getSuccessPayment(
            @QueryParam("href") String href,
            @QueryParam("vnp_TxnRef") String vnp_TxnRef,
            @QueryParam("vnp_OrderInfo") String vnp_OrderInfo,
            @QueryParam("vnp_PayDate") String vnp_PayDate,
            @QueryParam("vnp_ResponseCode") String vnp_ResponseCode,
            @QueryParam("vnp_Amount") Long vnp_Amount,
            @QueryParam("vnp_BankCode") String vnp_BankCode,
            @QueryParam("vnp_TransactionNo") String vnp_TransactionNo
    ) throws ParseException {
        UserDto userDto = (UserDto) session.getAttribute(SessionConstant.CURRENT_USER);
        Order order = orderService.create(userDto.id(), href, vnp_TxnRef, vnp_OrderInfo, vnp_PayDate, vnp_ResponseCode, vnp_Amount, vnp_BankCode, vnp_TransactionNo);
        if(order.getVnp_ResponseCode().equals(PaymentConstant.VNPAY_SUCCESS_CODE)){
            session.setAttribute("paySuccess", true);
        }else{
            session.setAttribute("paySuccess", false);
        }
        return "redirect:video/detail?v=" + href;
    }
}
