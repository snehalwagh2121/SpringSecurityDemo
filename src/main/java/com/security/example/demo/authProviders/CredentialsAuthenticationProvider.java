package com.security.example.demo.authProviders;

import com.security.example.demo.authentication.CredentialsAuthentication;
import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
        if (u != null) {
            if (passwordEncoder.matches(u.getPassword(), authentication.getCredentials().toString())) {
                u.setOtp(generateOTP());
                UsernamePasswordAuthenticationToken authenticationObj =
                        new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                authenticationObj.setDetails(u.getOtp());
                SecurityContextHolder.getContext().setAuthentication(authenticationObj);
                System.out.println("credentials are valid ");
                return authenticationObj;
            }
            System.out.println("credentials are not valid ");
            throw new
                    BadCredentialsException("External system authentication failed");
        }
        System.out.println("user is null");
        throw new
                BadCredentialsException("External system authentication failed");

    }

    private String generateOTP() {
        System.out.println("generating otp");
        String otp = String.valueOf(Math.random()).substring(2,8);
        return otp;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CredentialsAuthentication.class);
    }
}
