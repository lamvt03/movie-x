package com.filmweb.filter;

import com.filmweb.constant.CookieConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.service.UserService;
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

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // If the user is already logged in, do nothing
        if(req.getSession().getAttribute(SessionConstant.CURRENT_USER) != null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(CookieConstant.EMAIL_LOGIN))
                .findFirst()
                .map(Cookie::getValue)
                .flatMap(email -> Optional.ofNullable(userService.findByEmail(email)))
                                                .ifPresent(user -> req.getSession().setAttribute(SessionConstant.CURRENT_USER, user));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
