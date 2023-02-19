package com.security.example.demo.authProviders;

import com.security.example.demo.authentication.OtpAuthentication;
import com.security.example.demo.model.Otp;
import com.security.example.demo.repository.OtpRepository;
import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    OtpRepository otpRepository;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("username: " + authentication.getName());
        Otp otpFromDB = otpRepository.findOtpByUsername(authentication.getName());

        if (((String) authentication.getCredentials()).equals(otpFromDB.getOtp())) {
            log.info("otp is matching");
            log.info("getting user authorities from DB");
            CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
            UsernamePasswordAuthenticationToken authenticationObj =
                    new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationObj);
            return authenticationObj;
        }
        log.info("otp is not matching");
        throw new BadCredentialsException("invalid OTP");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OtpAuthentication.class);
    }
}
