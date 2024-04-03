package com.example.FlightSchoolManagement.repository;

import java.util.List;

import com.example.FlightSchoolManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByActive(int active);

    User findByEmail(String email);
}