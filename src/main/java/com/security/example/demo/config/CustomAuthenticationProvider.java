package com.security.example.demo.config;

import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Configuration
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailsService userDetailsService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authenticating user");
        CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
        if (u != null) {
            if (passwordEncoder.matches(u.getPassword(), authentication.getCredentials().toString())) {
                log.info("credetials are valid. Sending OTp in response");
                u.setOtp(generateOTP());
                UsernamePasswordAuthenticationToken authenticationObj =
                        new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                authenticationObj.setDetails(u.getOtp());
                SecurityContextHolder.getContext().setAuthentication(authenticationObj);
                return authenticationObj;
            }
            throw new
                    BadCredentialsException("External system authentication failed");
        }
        throw new
                BadCredentialsException("External system authentication failed");

    }

    private String generateOTP() {
        String otp = String.valueOf(Math.random()).substring(0,6);
        log.info("otp: "+otp);
        return otp;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
