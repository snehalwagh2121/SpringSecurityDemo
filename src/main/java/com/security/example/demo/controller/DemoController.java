package com.security.example.demo.controller;

import com.security.example.demo.model.Otp;
import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Autowired
    DemoServiceImpl demoService;


    @GetMapping("/validateDB")
    public boolean isDbAccessible() {
        return demoService.isDbAccessible();
    }

    @GetMapping("/admin")
    public String admin(Principal principal) {
       log.info("this api is accessible to you and the OTP is: {}" , principal.getName());
       return "admin";
    }

    @GetMapping("/manager")
    public String manager(Principal principal) {
        log.info( "this api is accessible to you and the OTP is: {}", principal.getName());
        return "manager";
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        log.info( "this api is accessible to you and the OTP is: {}", principal.getName());
        return "user";
    }

    @GetMapping("/otp")
    public String OtpPage(){
        log.info("inside otp page controller");
        return "otp";
    }

    @GetMapping("/getOtps")
    public List<Otp> getOtps(){
        log.info("inside get all otps controller");
        return demoService.getAllOtps();
    }
}
