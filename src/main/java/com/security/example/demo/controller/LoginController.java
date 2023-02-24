package com.security.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login success";
    }

    @GetMapping("/")
    public String homePage(){
        log.info("inside home page controller");
        return "This is the home page";
    }
}
