package com.security.example.demo.controller;

import com.security.example.demo.service.CustomUserDetails;
import com.security.example.demo.service.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
       return "this api is accessible to you and the OTP is: " + principal.getName();
    }

    @GetMapping("/manager")
    public String manager(Principal principal) {
        return "this api is accessible to you and the OTP is: " + principal.getName();
    }

    @GetMapping("/user")
    public String user(Principal principal) {
        return "this api is accessible to you and the OTP is: " + principal.getName();
    }

    @GetMapping("/otp")
    public String OtpPage(){
        log.info("inside otp page controller");
        return "otp";
    }

    @PostMapping("/otp")
    public String OtpVerifier(@ModelAttribute("otpForm") String otp, BindingResult bindingResult, Model model){
        log.info("inside otp verifier controller");
        boolean isValid= demoService.verifyOtp(otp);

        if (isValid)
            return "redirect:/demo/user";

        return "redirect:/demo/otp";
    }
}
