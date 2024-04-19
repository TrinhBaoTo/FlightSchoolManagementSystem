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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalDate.now;

@RestController
@Log4j
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    UserController userController;

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    AirportRepository airportRepository;

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getLesson(@RequestHeader("Authorization") String bearerToken){

        try {

            List<Flight> flights = new ArrayList<>();

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            if(userController.checkRole("I", _user)){

                flights = flightRepository.findByInstructor(_user);
            }

            if(userController.checkRole("S", _user)){

                flights = flightRepository.findByStudent(_user);
            }

            if (flights.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(flights, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flights/available")
    public ResponseEntity<List<Flight>> getAvailableLesson(@RequestHeader("Authorization") String bearerToken) {

        try {

            String token = extractToken(bearerToken);

            if(userController.checkUser(token)){

                List<Flight> flights = new ArrayList<>();

                flights = flightRepository.findAll();

                flights.removeIf(f -> f.getDepartureDateTime().before(Date.from(Instant.now())));

                flights.removeIf(f -> f.getStudent()!=null);

                return new ResponseEntity<>(flights, HttpStatus.OK);
            }

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/flight/instructor")
    public ResponseEntity<Flight> addLessonAsSInstructor(@RequestHeader("Authorization") String bearerToken,
                                                         @RequestParam int flightNumber, @RequestParam int aircraftRegNum,
                                                         @RequestParam String departureA, @RequestParam String arrivalA,
                                                         @RequestParam String departureDateTime, @RequestParam String arrivalDateTime) {

        try {

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            // check if user is an instructor
            if (userController.checkUser(token) && userController.checkRole("I", _user)) {

                Aircraft _aircraft = aircraftRepository.findByRegistrationNumber(aircraftRegNum);

                Airport _departure = airportRepository.findByCodeIata(departureA);
                Airport _arrival = airportRepository.findByCodeIata(arrivalA);

                Flight _flight = new Flight(flightNumber, _aircraft, _user, _departure, _arrival,
                        Timestamp.valueOf(departureDateTime), Timestamp.valueOf(arrivalDateTime));

                flightRepository.save(_flight);

                return new ResponseEntity<>(_flight, HttpStatus.OK);

            }else{

                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/flight/student")
    public ResponseEntity<String> addLessonAsStudent(@RequestHeader("Authorization") String bearerToken,
                                                     @RequestParam int flightNumber) {

        try {

            String token = extractToken(bearerToken);

            User _user = userController.getUser(token);

            // check if user is an instructor
            if (userController.checkUser(token) && userController.checkRole("S", _user)) {

                Flight _flight = flightRepository.findByFlightNumber(flightNumber);

                _flight.setStudent(_user);

                flightRepository.save(_flight);

                return new ResponseEntity<>("Flight Added for Student", HttpStatus.OK);

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
