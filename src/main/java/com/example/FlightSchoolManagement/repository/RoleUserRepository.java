package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, Integer> {
}
