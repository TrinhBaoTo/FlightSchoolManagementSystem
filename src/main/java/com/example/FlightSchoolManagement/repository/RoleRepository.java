package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByNameCode(String nameCode);
}
