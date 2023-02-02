package com.security.example.demo.service;

import com.security.example.demo.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CustomUserDetails implements UserDetails {

    Users user;
    String otp;
    public CustomUserDetails(Users user){
        this.user= user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role= user.getRole();
        log.info("role: "+role);
        List<SimpleGrantedAuthority> authorityList= new ArrayList<SimpleGrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(role)));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setOtp(String otp){
        this.otp = otp;
    }
    public String getOtp(){
        return this.otp;
    }
}
