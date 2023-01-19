package com.security.example.demo.service;

import com.security.example.demo.model.Users;
import com.security.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.findUserByUsername(username);
        if (u == null)
            throw new BadCredentialsException("Please enter correct credentials");
        log.info("user found in db: " + u.getUsername() + " ," + u.getPassword() + " , " + u.getRole());
        return new CustomUserDetails(u);
    }
}
