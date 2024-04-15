package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.extern.log4j.Log4j;

@RestController
@Log4j
public class AirportController {

    @Autowired
    AirportRepository airportRepository;

    // GET to retrieve all airport or retrieve airport with code
    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> getAllAirport(@RequestParam(required = false) String codeIata) {
        try {

            List<Airport> airports = new ArrayList<>();

            if (codeIata != null){
                airports = airportRepository.findAll();

            } else{
                airports.add(airportRepository.findByCodeIata(codeIata));
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
    public ResponseEntity<Airport> createAirport(@RequestParam String name, @RequestParam String codeIata) {
        try {

            Airport _airport = new Airport(name, codeIata);
            airportRepository.save(_airport);

            return new ResponseEntity<>(_airport, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new airport
    @PostMapping("/airport/file")
    public ResponseEntity<Airport> addAirportFile(@RequestParam String filePath) {

        try {

            readExcelForAirport(filePath);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void readExcelForAirport(String filePath) {

        File file = new File(filePath);

        try(Scanner scanner = new Scanner(file)){

            scanner.nextLine();

            //Read line
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] data = line.split(",");

                Airport _airport = new Airport(data[0], data[1]);
                airportRepository.save(_airport);
            }

        } catch (FileNotFoundException e) {

            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
