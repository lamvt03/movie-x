package com.filmweb.filter;

import static com.filmweb.constant.SessionConstant.CATEGORY_LIST;

import com.filmweb.constant.SessionConstant;
import com.filmweb.service.CategoryService;
import jakarta.inject.Inject;
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
            "/payment",
            "/profile",
            "/history",
            "/favorite",
            "/password",
            "/otp",
            "/verify",
            "/api",
            "/admin/video/",
            "/account",
            "/video/purchase",
            "/term-of-use",
            "/privacy-policy"
    };
    
    @Inject
    private CategoryService categoryService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HttpSession session = httpRequest.getSession(true);
        
        session.setAttribute(CATEGORY_LIST, categoryService.findAll());

        //store request path info to paginate
        String pathInFo = httpRequest.getPathInfo();
        session.setAttribute(SessionConstant.PATH_INFO, pathInFo);

        String requestURI = httpRequest.getRequestURI();
        if(isValidRequestURI(requestURI)){
            String queryString = httpRequest.getQueryString();
            String prevPageUri = requestURI.substring("/movie-x/".length());

            String prevPageUrl = createPrevPageUrl(prevPageUri, queryString);
            session.setAttribute(SessionConstant.PREV_PAGE_URL, prevPageUrl);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isValidRequestURI(String requestURI){
        for(String pattern: BLACK_LIST){
            if(requestURI.contains(pattern)){
                return false;
            }
        }
        return true;
    }

    private String createPrevPageUrl(String prevUri, String prevQueryString){
        StringBuilder urlBd = new StringBuilder(prevUri);
        if(prevQueryString != null){
            urlBd
                    .append("?")
                    .append(prevQueryString);
        }
        return urlBd.toString();
    }
}
