package com.filmweb.filter;

import com.filmweb.constant.SessionConstant;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/movie-x/*")
public class PrevPageRedirectFilter implements Filter {

    private final String[] BLACK_LIST = {
            "/login",
            "/logout",
            "/register",
            "/password",
            "/otp",
            "/verify",
            "/profile",
            "/payment",
            "/api",
            "/admin/video/"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;


        HttpSession session = httpRequest.getSession(true);

        //store request path info to paginate
        String pathInFo = httpRequest.getPathInfo();
        session.setAttribute(SessionConstant.PATH_INFO, pathInFo);

        String requestURI = httpRequest.getRequestURI();
        if(isValidRequestURI(requestURI)){
            String queryString = httpRequest.getQueryString();

            // Store the current URL in the session
            session.setAttribute(SessionConstant.PREV_PAGE_URI, requestURI.substring("/movie-x/".length()));
            session.setAttribute(SessionConstant.PREV_PAGE_QUERY_STRING, queryString);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
    private boolean isResourceRequest(String requestURI) {
        return requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".png") || requestURI.endsWith(".jpg");
    }

    private boolean isValidRequestURI(String requestURI){
        for(String pattern: BLACK_LIST){
            if(requestURI.contains(pattern)){
                return false;
            }
        }
        return !isResourceRequest(requestURI);
    }
}
