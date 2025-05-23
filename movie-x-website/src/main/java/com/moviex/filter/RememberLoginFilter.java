package com.moviex.filter;

import com.moviex.constant.CookieConstant;
import com.moviex.constant.SessionConstant;
import com.moviex.service.UserService;
import com.moviex.service.JwtService;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter("/movie-x/*")
public class RememberLoginFilter implements Filter {

    @Inject
    private UserService userService;

    @Inject
    private JwtService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // If the user is already logged in, do nothing
        if(req.getSession().getAttribute(SessionConstant.CURRENT_USER) != null
                || req.getCookies() == null
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(CookieConstant.REMEMBER_TOKEN))
                .filter(cookie -> cookie.getMaxAge() > 0)
                .findFirst()
                .map(Cookie::getValue)
                .filter(token -> !jwtService.isTokenExpired(token))
                .map(jwtService::extractSubject)
                .flatMap(email -> Optional.ofNullable(userService.findByEmail(email)))
                .ifPresent(user -> req.getSession().setAttribute(SessionConstant.CURRENT_USER, user));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

