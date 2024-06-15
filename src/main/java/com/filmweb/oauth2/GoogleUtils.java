package com.filmweb.oauth2;

import com.filmweb.constant.AppConstant;
import com.filmweb.dto.GoogleUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class GoogleUtils {

    public static String scope = "email+profile";
    public static String redirect_uri = AppConstant.HOST_URL + "/movie-x/oauth2/login/google/callback";
    public static String response_type = "code";
    public static String client_id = "14458673516-4c8246q5dbb3be37hmaeu3t6hqijacmf.apps.googleusercontent.com";
    public static String client_secret = "";
    public static String approval_prompt = "force";
    public static String grant_type = "authorization_code";

    public static String TOKEN_URL = "https://oauth2.googleapis.com/token";
    public static String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    public static String getLoginUrl() {
        return "https://accounts.google.com/o/oauth2/auth?scope=" + scope + "&redirect_uri=" + redirect_uri + "&response_type=" + response_type + "&client_id=" + client_id + "&approval_prompt=" + approval_prompt;
    }

    public static String getToken(String code) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        // Create a trust manager that does not validate certificate chains
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, (chain, authType) -> true);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                sslsf).build();

        HttpPost httpPost = new HttpPost(TOKEN_URL);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("client_secret", client_secret));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("grant_type", grant_type));
        params.add(new BasicNameValuePair("redirect_uri", redirect_uri));

        // Execute and get the response.
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpclient.execute(httpPost);

        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        JsonObject jsonObject = new Gson().fromJson(jsonStr, JsonObject.class);
        return jsonObject.get("access_token").toString().replaceAll("\"", "");
    }

    public static GoogleUser getUserInfo(final String accessToken) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String link = USER_INFO_URL + accessToken;

        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, (chain, authType) -> true);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                sslsf).build();

        HttpGet httpGet = new HttpGet(link);
        HttpResponse response = httpclient.execute(httpGet);

        String jsonObject = EntityUtils.toString(response.getEntity(), "UTF-8");
        return new Gson().fromJson(jsonObject, GoogleUser.class);
    }
}
