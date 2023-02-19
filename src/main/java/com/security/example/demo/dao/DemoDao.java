package com.security.example.demo.dao;


import com.security.example.demo.model.Otp;
import com.security.example.demo.model.Users;

import java.util.List;

public interface DemoDao {

    Users getUser(String snehal);

    List<Otp> getAllOtps();
}
