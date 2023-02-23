package com.security.example.demo.filter;

import com.security.example.demo.authProviders.JWTAuthenticationProvider;
import com.security.example.demo.authentication.JWTAuthentication;
import com.security.example.demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

//    public JWTFilter(AuthenticationManager authenticationManager){
//        this.authenticationManager= authenticationManager;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String username= httpServletRequest.getHeader("username");
        String authorizationToken= httpServletRequest.getHeader("authorization");

        if(authorizationToken!= null && authorizationToken.startsWith("Bearer ")){
            log.info("inside authorization token validation");
            String jwtToken = authorizationToken.substring(7, authorizationToken.length());
            log.info("token: "+jwtToken);
            UsernamePasswordAuthenticationToken token= new JWTAuthentication(username, jwtToken);
            authenticationManager.authenticate(token);
        }else{
            throw new BadCredentialsException("Invalid authorizationToken");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login"); //these will not be calling the filter
    }
}
