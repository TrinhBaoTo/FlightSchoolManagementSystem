package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.repository.AirportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AirportController {

    @Autowired
    AirportRepository airportRepository;

    // GET to retrieve all airport or retrieve airport with code
    @GetMapping("/airport")
    public ResponseEntity<List<Airport>> getAllAirport(@RequestParam(required = false) String codeIata) {
        try {
            List<Airport> airports = new ArrayList<>();

            if (codeIata == null){
                airports.addAll(airportRepository.findAll());
            } else{
                airports.addAll(airportRepository.findByCodeIata(codeIata));
            }

            if (airports.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(airports, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new airport
    @PostMapping("/airport")
    public ResponseEntity<Airport> createAirport(@RequestParam String codeIata) {
        try {

            Airport _airport = airportRepository.save(new Airport(codeIata));
            return new ResponseEntity<>(_airport, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
