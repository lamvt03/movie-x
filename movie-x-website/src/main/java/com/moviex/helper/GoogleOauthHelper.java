package com.moviex.helper;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.moviex.config.GoogleOauth2ConfigurationProperties;
import com.moviex.dto.GoogleUser;
import com.moviex.exception.MovieXException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.security.GeneralSecurityException;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperties;

import java.io.IOException;

@ApplicationScoped
public class GoogleOauthHelper {
    
    private static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final HttpTransport HTTP_TRANSPORT;
    private static final String GOOGLE_OAUTH2_URL = "https://accounts.google.com/o/oauth2/auth";
  
    static {
      try {
        HTTP_TRANSPORT = new NetHttpTransport.Builder().doNotValidateCertificate().build();
      } catch (GeneralSecurityException e) {
        throw new MovieXException(e);
      }
    }
  
    @Inject
    @ConfigProperties
    private GoogleOauth2ConfigurationProperties googleOauth2ConfigurationProperties;

    public String createLoginUrl() {
        try {
            var uriBuilder = new URIBuilder(GOOGLE_OAUTH2_URL);
            uriBuilder.addParameter("scope", googleOauth2ConfigurationProperties.getScope());
            uriBuilder.addParameter("redirect_uri", googleOauth2ConfigurationProperties.getRedirectUri());
            uriBuilder.addParameter("response_type", googleOauth2ConfigurationProperties.getResponseType());
            uriBuilder.addParameter("client_id", googleOauth2ConfigurationProperties.getClientId());
            uriBuilder.addParameter("approval_prompt", googleOauth2ConfigurationProperties.getApprovalPrompt());
            
            return uriBuilder.build().toString();
        } catch (Exception e) {
            throw new MovieXException("Failed to build Google OAuth URL", e);
        }
    }
    
    private String getAccessToken(String authorizationCode) {
        try {
            var tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                HTTP_TRANSPORT, JSON_FACTORY,
                googleOauth2ConfigurationProperties.getClientId(),
                googleOauth2ConfigurationProperties.getClientSecret(),
                authorizationCode,
                googleOauth2ConfigurationProperties.getRedirectUri()
            ).execute();
            
            return tokenResponse.getAccessToken();
        } catch (Exception e) {
            throw new MovieXException(e);
        }
    }

    public GoogleUser getUserInfo(final String authorizationCode) {
      var accessToken = getAccessToken(authorizationCode);
      try {
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        
        Oauth2 oauth2 = new Oauth2.Builder(
            HTTP_TRANSPORT,
            JSON_FACTORY,
            credential).build();
        
        // Get userInfo using credential
        Userinfo userInfo = oauth2.userinfo().get().execute();
        
        return GoogleUser.builder()
          .email(userInfo.getEmail())
          .name(userInfo.getName())
          .picture(userInfo.getPicture())
          .build();
        
      } catch (IOException e) {
        return null;
      }
    }
}
