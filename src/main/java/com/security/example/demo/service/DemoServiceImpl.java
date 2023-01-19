package com.security.example.demo.service;

import com.security.example.demo.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoDao dao;

    public boolean isDbAccessible() {
        return (dao.getUser("snehal") != null);
    }
}
