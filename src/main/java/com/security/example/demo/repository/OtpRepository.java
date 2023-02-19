package com.security.example.demo.repository;

import com.security.example.demo.model.Otp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface OtpRepository extends CrudRepository<Otp, String> {

    Otp findOtpByUsername(String name);
}
