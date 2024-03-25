package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.model.User;

public interface UserRepository extends JpaRepository<User, int> {
    List<User> findByActive(int active);
    List<User> findByEmail(String email);
}