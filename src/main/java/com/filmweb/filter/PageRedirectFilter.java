package com.filmweb.filter;

import com.filmweb.constant.SessionConstant;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/app/*")
public class PageRedirectFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HttpSession session = httpRequest.getSession(true);

        String requestURI = httpRequest.getRequestURI();
        if(!requestURI.contains("/login") && !requestURI.contains("/logout")
            && !requestURI.contains("/password") && !requestURI.contains("/otp")
            && !requestURI.contains("/register")  && !requestURI.contains("/verify")
            && !requestURI.contains("/profile") && !isResourceRequest(requestURI)){
            String queryString = httpRequest.getQueryString();

            // Store the current URL in the session
            session.setAttribute(SessionConstant.PREV_PAGE_URI, requestURI.substring("/app/".length()));
            session.setAttribute(SessionConstant.PREV_PAGE_QUERY_STRING, queryString);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
    private boolean isResourceRequest(String requestURI) {
        return requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".png");
    }
}
