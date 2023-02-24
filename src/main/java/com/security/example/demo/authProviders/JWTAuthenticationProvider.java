package com.security.example.demo.authProviders;

import com.security.example.demo.authentication.JWTAuthentication;
import com.security.example.demo.service.CustomUserDetailsService;
import com.security.example.demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("inside JWT authorization Provider");
        String username= authentication.getName();
        String jwtToken= (String) authentication.getCredentials();
        log.info("jwt token in authentication provider: "+jwtToken);
        UserDetails userDetails= userDetailsService.loadUserByUsername(username);
        log.info("user details form DB username: "+userDetails.getUsername());
        jwtUtil.validateToken(jwtToken, userDetails);
        UsernamePasswordAuthenticationToken authenticationObj =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationObj);
        System.out.println("credentials are valid ");
        return authenticationObj;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JWTAuthentication.class);
    }
}
