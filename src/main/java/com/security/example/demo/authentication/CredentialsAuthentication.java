package com.security.example.demo.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CredentialsAuthentication extends UsernamePasswordAuthenticationToken {
    public CredentialsAuthentication(Object principal, Object credentials){
        super(principal, credentials);
    }
}
