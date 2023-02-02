package com.security.example.demo.service;

import com.security.example.demo.dao.DemoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        log.info("otp sent in form: "+otp);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("details: "+authentication.getDetails());
        return authentication.getDetails().equals(otp);
    }
}
