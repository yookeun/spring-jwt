package com.example.springjwt.repository;

import com.example.springjwt.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUserByUserId(String userId);
}

