package com.security.example.demo.dao;

import com.security.example.demo.model.Otp;
import com.security.example.demo.model.Users;
import com.security.example.demo.repository.DemoRepository;
import com.security.example.demo.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class DemoDaoImpl implements DemoDao {
    @Autowired
    DemoRepository repository;

    @Autowired
    OtpRepository otpRepository;

    @Override
    public Users getUser(String snehal) {
        Users isFound = repository.getUserByUsername(snehal);
        log.info("isfound = " + isFound);
        return isFound;
    }

    @Override
    public List<Otp> getAllOtps() {
        return (List<Otp>) otpRepository.findAll();
    }

    @Override
    public void setOtp(Otp otp) {
        otpRepository.save(otp);
    }
}
