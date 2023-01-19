package com.security.example.demo.dao;

import com.security.example.demo.model.Users;
import com.security.example.demo.repository.DemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DemoDaoImpl implements DemoDao {
    @Autowired
    DemoRepository repository;

    @Override
    public Users getUser(String snehal) {
        Users isFound = repository.getUserByUsername(snehal);
        log.info("isfound = " + isFound);
        return isFound;
    }
}
