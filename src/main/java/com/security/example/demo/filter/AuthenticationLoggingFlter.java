package com.security.example.demo.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class AuthenticationLoggingFlter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var requestId =
                httpRequest.getHeader("Request-Id");
        log.info("Successfully authenticated request with id " + requestId);
        filterChain.doFilter(request, response);
    }
}
