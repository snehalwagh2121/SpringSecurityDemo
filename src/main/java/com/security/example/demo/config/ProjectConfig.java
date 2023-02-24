package com.security.example.demo.config;

import com.security.example.demo.authProviders.CredentialsAuthenticationProvider;
import com.security.example.demo.authProviders.JWTAuthenticationProvider;
import com.security.example.demo.authProviders.OtpAuthenticationProvider;
import com.security.example.demo.filter.JWTFilter;
import com.security.example.demo.filter.OtpFilter;
import com.security.example.demo.passwordEncoder.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    CredentialsAuthenticationProvider credentialsAuthProvider;
//
//    @Autowired
//    OtpAuthenticationProvider otpAuthenticationProvider;
//
//    @Autowired
//    JWTAuthenticationProvider jwtAuthenticationProvider;

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new CustomPasswordEncoder();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) {
//        System.out.println("configuring auth providers");
//        auth.authenticationProvider(credentialsAuthProvider)
//                .authenticationProvider(otpAuthenticationProvider)
//                .authenticationProvider(jwtAuthenticationProvider);
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterAt(otpFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(jwtFilter(), BasicAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/demo/admin")
//                .hasAuthority("ADMIN")
//                .antMatchers("/demo/manager")
//                .hasAnyAuthority("ADMIN", "MANAGER")
//                .antMatchers("/demo/user")
//                .hasAnyAuthority("USER", "MANAGER", "ADMIN")
//                .antMatchers("/demo/otp")
//                .permitAll()
//                .antMatchers("/otp")
//                .permitAll()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login();
        ;
    }

//    @Bean
//    public OtpFilter otpFilter() {
//        return new OtpFilter();
//    }
//
//    @Bean
//    public JWTFilter jwtFilter() {
//        return new JWTFilter();
//    }
//
//    @Override
//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return new ProviderManager(Arrays.asList((AuthenticationProvider) otpAuthenticationProvider,
//                (AuthenticationProvider) credentialsAuthProvider,
//                (AuthenticationProvider) jwtAuthenticationProvider));
//    }

}
