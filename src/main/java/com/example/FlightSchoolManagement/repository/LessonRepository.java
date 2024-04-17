package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Lesson;
import com.example.FlightSchoolManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository  extends JpaRepository<Lesson, Integer> {

    Lesson findById(int id);

    List<Lesson> findByInstructor(User _user);

    List<Lesson> findByLessonName(String lessonName);
}
