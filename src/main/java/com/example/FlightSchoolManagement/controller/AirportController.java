package com.example.FlightSchoolManagementSystem;
package com.bezkoder.spring.jpa.postgresql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.FlightSchoolManagement.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
            List<Airport> airports = new ArrayList<Airport>();

            if (codeIata == null){
                airportRepository.findAll().forEach(airports::add);
            } else{
                airportRepository.findByCode(codeIata).forEach(airports::add);
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

        if (airportData.isPresent()) {
            ResponseEntity<Airport> airportResponseEntity = new ResponseEntity<>(airportData.get(), HttpStatus.OK);
            return airportResponseEntity;
        } else {
            ResponseEntity<Airport> airportResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return airportResponseEntity;
        }
    }

    // POST: add a new airport
    @PostMapping("/airports")
    public ResponseEntity<Airport> createAirport(@RequestParam String codeIata) {
        try {

            Airport _airport = airportRepository.save(new Airport(codeIata));
            ResponseEntity<Airport> airportResponseEntity = new ResponseEntity<>(_airport, HttpStatus.CREATED);
            return airportResponseEntity;

        } catch (Exception e) {
            ResponseEntity<Airport> airportResponseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            return airportResponseEntity;
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
