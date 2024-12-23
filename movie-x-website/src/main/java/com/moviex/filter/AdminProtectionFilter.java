package com.moviex.filter;

import com.moviex.constant.SessionConstant;
import com.moviex.dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/movie-x/admin/*")
public class AdminProtectionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getPathInfo();
        HttpSession session = req.getSession();
        UserDto admin = (UserDto) session.getAttribute(SessionConstant.CURRENT_ADMIN);
        if (path.equals("/admin") || admin != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect("/movie-x/admin");
        }

    }
}
