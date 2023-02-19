package com.security.example.demo.service;

import com.security.example.demo.dao.DemoDao;
import com.security.example.demo.model.Otp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoDao dao;

    public boolean isDbAccessible() {
        return (dao.getUser("snehal") != null);
    }

    @Override
    public boolean verifyOtp(String otp) {
        log.info("otp sent in form: " + otp);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("details: " + authentication.getDetails());
        return authentication.getDetails().equals(otp);
    }

    @Override
    public List<Otp> getAllOtps() {
        return dao.getAllOtps();
    }

    @Override
    public void setOtp(Otp otp) {
        dao.setOtp(otp);
    }
}
