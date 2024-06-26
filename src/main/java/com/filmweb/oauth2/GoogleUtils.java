package com.filmweb.oauth2;

import com.filmweb.dto.GoogleUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class GoogleUtils {

    @Inject
    @ConfigProperty(name = "oauth2.login.google.clientId")
    private String client_id;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.clientSecret")
    private String client_secret;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.scope")
    private String scope;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.redirectUri")
    private String redirect_uri;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.responseType")
    private String response_type;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.approvalPrompt")
    private String approval_prompt;

    @Inject
    @ConfigProperty(name = "oauth2.login.google.grantType")
    private String grant_type;

    @Inject
    @ConfigProperty(name="oauth2.login.google.tokenUrl")
    private String tokenUrl;

    @Inject
    @ConfigProperty(name="oauth2.login.google.userInfoUrl")
    private String userInfoUrl;

    public String createLoginUrl() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://accounts.google.com/o/oauth2/auth")
                .append("?scope=").append(scope)
                .append("&redirect_uri=").append(redirect_uri)
                .append("&response_type=").append(response_type)
                .append("&client_id=").append(client_id)
                .append("&approval_prompt=").append(approval_prompt);
        return urlBuilder.toString();
    }


    public String getToken(String code) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        // Create a trust manager that does not validate certificate chains
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, (chain, authType) -> true);
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                sslsf).build();

        HttpPost httpPost = new HttpPost(tokenUrl);
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

    public GoogleUser getUserInfo(final String accessToken) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String link = userInfoUrl +  "?access_token=" + accessToken;

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
