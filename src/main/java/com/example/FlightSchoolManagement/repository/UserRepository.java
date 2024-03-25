package com.example.FlightSchoolManagement.repository;

import java.util.List;

import com.example.FlightSchoolManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByActive(int active);
    List<User> findByEmail(String email);
}