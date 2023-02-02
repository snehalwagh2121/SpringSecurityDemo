package com.security.example.demo.config;

import com.security.example.demo.filter.AuthenticationLoggingFlter;
import com.security.example.demo.passwordEncoder.CustomPasswordEncoder;
import com.security.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider authProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        //The commented will also work if we dont have custom authenctication provider.
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new OtpFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/demo/admin")
                .hasAnyAuthority("ADMIN")
                .antMatchers("/demo/manager")
                .hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/demo/user")
                .hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .antMatchers("/demo/otp")
                .authenticated()
                .anyRequest().authenticated()
                .and().formLogin();
    }
}
