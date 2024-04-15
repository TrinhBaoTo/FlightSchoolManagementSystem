package com.example.FlightSchoolManagement.controller;


import com.example.FlightSchoolManagement.entity.Manufacturer;
import com.example.FlightSchoolManagement.repository.ManufacturerRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j
public class ManufacturerController {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    // GET to retrieve all manufacturers
    @GetMapping("/manufacturers")
    public ResponseEntity<List<Manufacturer>> getAllManufacturer(@RequestParam(required = false) String name) {
        try {

            List<Manufacturer> manufacturers = new ArrayList<>();

            if (name != null){
                manufacturers = manufacturerRepository.findAll();

            } else{
                manufacturers.add(manufacturerRepository.findByName(name));
            }

            if (manufacturers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(manufacturers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new manufacturer
    @PostMapping("/manufacturer")
    public ResponseEntity<Manufacturer> createManufacturer(@RequestParam String name) {
        try {

            Manufacturer _manufacturer = new Manufacturer(name);
            manufacturerRepository.save(_manufacturer);

            return new ResponseEntity<>(_manufacturer, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new manufacturer
    @DeleteMapping("/manufacturer")
    public ResponseEntity<String> deleteManufacturer(@RequestParam int id) {
        try {

            manufacturerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
