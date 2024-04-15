package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Aircraft;
import com.example.FlightSchoolManagement.entity.Manufacturer;
import com.example.FlightSchoolManagement.repository.AircraftRepository;
import com.example.FlightSchoolManagement.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class AircraftController {

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    // GET to retrieve all aircraft or retrieve aircraft with code
    @GetMapping("/aircrafts")
    public ResponseEntity<List<Aircraft>> getAllAircraft(@RequestParam(required = false) int registrationNumber) {
        try {

            List<Aircraft> aircrafts = new ArrayList<>();

            if (registrationNumber == -1){
                aircrafts = aircraftRepository.findAll();

            } else{
                aircrafts.add(aircraftRepository.findByRegistrationNumber(registrationNumber));
            }

            if (aircrafts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(aircrafts, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new aircraft
    @PostMapping("/aircraft")
    public ResponseEntity<Aircraft> createAirport(@RequestParam  int registrationNumber, @RequestParam String model, @RequestParam String manufacturer,
                                                  @RequestParam int yearManufactured, @RequestParam double purchasePrice,
                                                  @RequestParam("purchaseDate") @DateTimeFormat(pattern = "dd.MM.yyyy") Date purchaseDate) {
        try {

            Manufacturer _manufacturer = manufacturerRepository.findByName(manufacturer);
            int availability = 1;

            Aircraft _aircraft = new Aircraft(registrationNumber, model, _manufacturer, yearManufactured, purchaseDate , purchasePrice, availability);
            aircraftRepository.save(_aircraft);

            return new ResponseEntity<>(_aircraft, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
