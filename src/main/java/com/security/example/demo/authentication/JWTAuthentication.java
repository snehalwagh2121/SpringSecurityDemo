package com.security.example.demo.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JWTAuthentication extends UsernamePasswordAuthenticationToken {
    public JWTAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
