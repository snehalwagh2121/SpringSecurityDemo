package com.security.example.demo.filter;

import com.security.example.demo.authProviders.CredentialsAuthenticationProvider;
import com.security.example.demo.authProviders.OtpAuthenticationProvider;
import com.security.example.demo.authentication.CredentialsAuthentication;
import com.security.example.demo.authentication.OtpAuthentication;
import com.security.example.demo.config.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OtpFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    public OtpFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("do     filter called");

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        log.info("username: " + username + " password: " + password + " otp: " + otp);

        if (otp == null) {
            Authentication authentication = new CredentialsAuthentication(username, password);
            authenticationManager.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, otp);
            authenticationManager.authenticate(authentication);
        }

        filterChain.doFilter(request, response);
        log.info("returning from filter");
        return;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/demo/getOtps"); //these will not be calling filters
    }
}