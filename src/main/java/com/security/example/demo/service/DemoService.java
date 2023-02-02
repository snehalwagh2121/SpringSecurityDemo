package com.security.example.demo.service;

public interface DemoService {

    boolean isDbAccessible();

    boolean verifyOtp(String otp);
}
