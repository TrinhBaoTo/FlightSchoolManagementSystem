package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.RoleUser;
import com.example.FlightSchoolManagement.entity.RoleUserId;
import com.example.FlightSchoolManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserId> {

    List<RoleUser> findByUser(User _user);
}
