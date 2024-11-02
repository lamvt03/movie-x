package com.moviex.helper;

import com.moviex.config.VNPayConfigurationProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperties;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class VNPayHelper {
    
    private static final String PAYMENT_TRANSACTION_INFO_PATTERN = "Thanh toan giao dich nap tien %s";
    
    @Inject
    @ConfigProperties(prefix = "vnp")
    private VNPayConfigurationProperties vnPayConfigurationProperties;

    private String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    public String createPaymentUrl(UUID paymentTransactionId, long amount, String clientIp){

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayConfigurationProperties.getVersion());
        vnp_Params.put("vnp_Command", vnPayConfigurationProperties.getCommand());
        vnp_Params.put("vnp_TmnCode", vnPayConfigurationProperties.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        // vnp_Params.put("vnp_BankCode", vnPayConfigurationProperties.getBankCode());
        vnp_Params.put("vnp_TxnRef", paymentTransactionId.toString());
        vnp_Params.put("vnp_OrderInfo", buildPaymentTransactionInfo(paymentTransactionId));
        vnp_Params.put("vnp_OrderType", vnPayConfigurationProperties.getOrderType());
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfigurationProperties.getReturnUrl());
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
        String vnp_SecureHash = hmacSHA512(vnPayConfigurationProperties.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnPayConfigurationProperties.getPayUrl() + "?" + queryUrl;
    }
    
    private String buildPaymentTransactionInfo(UUID paymentTransactionId) {
        return String.format(PAYMENT_TRANSACTION_INFO_PATTERN, paymentTransactionId.toString());
    }
}
