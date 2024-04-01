package com.example.FlightSchoolManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AirportController {

    @Autowired
    AirportRepository airportRepository;

    // GET to retrieve all airport or retrieve airport with code
    @GetMapping("/airports")
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

    @GetMapping("/airports/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable("id") int id) {

        Optional<Airport> airportData = airportRepository.findById(id);

        ResponseEntity<Airport> airportResponseEntity;
        if (airportData.isPresent()) {
            airportResponseEntity = new ResponseEntity<>(airportData.get(), HttpStatus.OK);
        } else {
            airportResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return airportResponseEntity;
    }

    // POST: add a new airport
    @PostMapping("/airports")
    public ResponseEntity<Airport> createAirport(@RequestParam String codeIata) {
        try {

            Airport _airport = airportRepository.save(new Airport(codeIata));
            return new ResponseEntity<>(_airport, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT: update an existing airport
    @PutMapping("/airports/{id}")
    public String updateUser(@PathVariable("id") Integer id, @RequestParam String name) {
        return("");
    }

    // DELETE: delete an airport by ID
    @DeleteMapping("/airports/{id}")
     public ResponseEntity<HttpStatus> deleteAirport(@PathVariable("id") Integer id) {
        try {
            airportRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Does not support CONNECT but have PATCH - apply partial modifications to a resource.
}
