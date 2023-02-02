package com.security.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OtpFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("inside otp filter ");
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.info("authentication is null");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!authentication.isAuthenticated()) {
            log.info("user is not authenticated, redirecting to /login");
            httpResponse.sendRedirect("/login");
            return;
        }

        httpResponse.sendRedirect("/demo/otp");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
