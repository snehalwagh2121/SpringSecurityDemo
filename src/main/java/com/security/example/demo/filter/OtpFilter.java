package com.security.example.demo.filter;

import com.security.example.demo.authentication.CredentialsAuthentication;
import com.security.example.demo.authentication.OtpAuthentication;
import com.security.example.demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class OtpFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

//    public OtpFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("do filter called");

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");
        String authorizationToken= request.getHeader("authorization");
        log.info("username: " + username + " password: " + password + " otp: " + otp +" authorization: "+authorizationToken);

        if(authorizationToken == null){
            if (otp == null) {
                Authentication authentication = new CredentialsAuthentication(username, password);
                authentication = authenticationManager.authenticate(authentication);
                log.info("otp in response: " + authentication.getDetails());
                response.setHeader("otp", (String) authentication.getDetails());
            } else {
                Authentication authentication = new OtpAuthentication(username, otp);
                authenticationManager.authenticate(authentication);
                Authentication auth= SecurityContextHolder.getContext().getAuthentication();
                log.info("sending authorization token back");
                response.setHeader("authorization" ,jwtUtil.generateToken((UserDetails) auth.getPrincipal()));
            }
        }

        filterChain.doFilter(request, response);
        log.info("returning from filter");
        return;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login"); //these will be calling the filter
    }
}
