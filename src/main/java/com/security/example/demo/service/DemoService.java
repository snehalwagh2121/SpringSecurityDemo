package com.security.example.demo.service;

import com.security.example.demo.model.Otp;

import java.util.List;

public interface DemoService {

    boolean isDbAccessible();

    boolean verifyOtp(String otp);

    List<Otp> getAllOtps();

    void setOtp(Otp otp);
}
