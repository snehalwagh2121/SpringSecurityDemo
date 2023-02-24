package com.security.example.demo.authProviders;

import com.security.example.demo.authentication.CredentialsAuthentication;
import com.security.example.demo.model.Otp;
import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.CustomUserDetailsService;
import com.security.example.demo.service.DemoService;
import com.security.example.demo.util.ProjectConstants;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class CredentialsAuthenticationProvider implements AuthenticationProvider {

    static final String twilioRegisteredno = ProjectConstants.TWILIO_REGISTERED_NO;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    DemoService demoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomUserDetails u = userDetailsService.loadUserByUsername(authentication.getName());
        if (u != null) {
            log.info("u.mobileno= " + u.getMobileno());
            if (passwordEncoder.matches(u.getPassword(), authentication.getCredentials().toString())) {
                u.setOtp(generateOTP(u.getUsername(), u.getMobileno()));
                UsernamePasswordAuthenticationToken authenticationObj =
                        new UsernamePasswordAuthenticationToken(u, null, null);
                authenticationObj.setDetails(u.getOtp());
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

    private String generateOTP(String username, String mobileno) {
        System.out.println("generating otp");
        String otp = String.valueOf(Math.random()).substring(2, 8);
        Otp otpObj = new Otp(username, otp);

//        Message.creator(new PhoneNumber(mobileno),
//                new PhoneNumber(twilioRegisteredno), "Hi the OTP to login is: " + otp).create();
        demoService.setOtp(otpObj);
        return otp;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CredentialsAuthentication.class);
    }
}
