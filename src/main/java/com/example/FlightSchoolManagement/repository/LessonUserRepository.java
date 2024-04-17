package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonUserRepository extends JpaRepository<LessonUser, LessonUserId>{

    List<LessonUser> findByUser(User _user);
}
