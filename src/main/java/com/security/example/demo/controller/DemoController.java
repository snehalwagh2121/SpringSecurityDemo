package com.security.example.demo.controller;

import com.security.example.demo.service.DemoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoServiceImpl demoService;

    @GetMapping("/validateDB")
    public boolean isDbAccessible() {
        return demoService.isDbAccessible();
    }

    @GetMapping("/admin")
    public String admin() {
        return "this api is accessible to you";
    }

    @GetMapping("/manager")
    public String manager() {
        return "this api is accessible to you";
    }

    @GetMapping("/user")
    public String user() {
        return "this api is accessible to you";
    }
}
