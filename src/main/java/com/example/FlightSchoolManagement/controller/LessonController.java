package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.*;
import com.example.FlightSchoolManagement.repository.*;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j
public class LessonController {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonUserRepository lessonUserRepository;

    @Autowired
    UserController userController;

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    StatusRepository statusRepository;

    // GET to retrieve all or user by email
    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getLesson(@RequestHeader("Authorization") String bearerToken){

        try {

            List<Lesson> lessons = new ArrayList<>();

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            if(userController.checkRole("I", _user)){

                lessons = lessonRepository.findByInstructor(_user);
            }

            if(userController.checkRole("S", _user)){

                List<LessonUser>_lessonUser = lessonUserRepository.findByUser(_user);

                for( LessonUser lu : _lessonUser){

                    lessons.add(lu.getLesson());
                }
            }

            if (lessons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(lessons, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET to retrieve all or user by email
    @GetMapping("/lessons/available")
    public ResponseEntity<List<Lesson>> getAvailableLesson(@RequestHeader("Authorization") String bearerToken, @RequestParam String lessonName){

        try {

            String token = extractToken(bearerToken);

            if(userController.checkUser(token)){

                List<Lesson> lessons = new ArrayList<>();

                if(!lessonName.isEmpty()){

                    Status s = statusRepository.findByName("Scheduled");
                    lessons = lessonRepository.findByLessonName(lessonName);

                    for(Lesson l : lessons){

                        if(l.getStatus()!=s){
                            lessons.remove(l);
                        }
                    }
                }

                return new ResponseEntity<>(lessons, HttpStatus.OK);

            }

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/lesson/instructor")
    public ResponseEntity<Lesson> addLessonAsSInstructor(@RequestHeader("Authorization") String bearerToken,
                                                         @RequestParam String lessonName, @RequestParam int aircraftRegNum,
                                                         @RequestParam String durationStr, @RequestParam String lessonDateTime, @RequestParam String notes) {

        try {

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            // check if user is an instructor
            if (userController.checkUser(token) && userController.checkRole("I", _user)) {

                Aircraft _aircraft = aircraftRepository.findByRegistrationNumber(aircraftRegNum);

                Duration duration = Duration.parse(durationStr);

                Lesson _lesson = new Lesson(lessonName, _user, _aircraft, duration,
                                            Timestamp.valueOf(lessonDateTime), statusRepository.findByName("Scheduled"), notes);

                lessonRepository.save(_lesson);

                return new ResponseEntity<>(_lesson, HttpStatus.OK);

            }else{

                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/lesson/student")
    public ResponseEntity<String> addLessonAsStudent(@RequestHeader("Authorization") String bearerToken,
                                                     @RequestParam int lessonId) {

        try {

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            // check if user is an instructor
            if (userController.checkUser(token) && userController.checkRole("S", _user)) {

                LessonUser _lessonUser = new LessonUser(userController.getUser(token),lessonRepository.findById(lessonId));
                lessonUserRepository.save(_lessonUser);

                return new ResponseEntity<>("Lesson Added for Student", HttpStatus.OK);

            }else{

                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractToken(String bearerToken) {

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            // Remove "Bearer " prefix
            return bearerToken.substring(7);
        }

        return null;
    }
}
