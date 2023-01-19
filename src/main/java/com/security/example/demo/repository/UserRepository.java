package com.security.example.demo.repository;

import com.security.example.demo.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, String> {

    Users findUserByUsername(String name);

}
