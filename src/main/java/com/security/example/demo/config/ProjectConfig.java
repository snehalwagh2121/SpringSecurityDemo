package com.security.example.demo.config;

import com.security.example.demo.authProviders.CredentialsAuthenticationProvider;
import com.security.example.demo.authProviders.OtpAuthenticationProvider;
import com.security.example.demo.authentication.OtpAuthentication;
import com.security.example.demo.filter.OtpFilter;
import com.security.example.demo.passwordEncoder.CustomPasswordEncoder;
import com.security.example.demo.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CredentialsAuthenticationProvider authProvider;

    @Autowired
    OtpAuthenticationProvider otpAuthenticationProvider;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        System.out.println("configuring auth providers");
        auth.authenticationProvider(authProvider)
        .authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
         http
                .addFilterAt(new OtpFilter(authenticationManager()), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/demo/admin")
                .hasAnyAuthority("ADMIN")
                .antMatchers("/demo/manager")
                .hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/demo/user")
                .hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .antMatchers("/demo/otp")
                .permitAll()
                .antMatchers("/otp")
                .permitAll()
//                .anyRequest().authenticated()
                ;
    }


//    @Override
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        System.out.println("authentication Manager bean");
//        AuthenticationManager as = super.authenticationManagerBean();
//        return as;
//    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList((AuthenticationProvider) otpAuthenticationProvider,
                (AuthenticationProvider) authProvider));
    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        authenticationManagerBuilder.authenticationProvider(otpAuthenticationProvider);
//        return authenticationManagerBuilder.build();
//    }


}
