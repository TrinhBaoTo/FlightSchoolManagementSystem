package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Status;
import com.example.FlightSchoolManagement.repository.StatusRepository;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Log4j
public class StatusController {

    @Autowired
    StatusRepository statusRepository;


    // GET to retrieve all status
    @GetMapping("/statuses")
    public ResponseEntity<List<Status>> getAllStatus(@RequestParam(required = false) String name) {
        try {

            List<Status> statuses = new ArrayList<>();

            if(name != null){

                statuses = statusRepository.findAll();

            } else{
                statuses.add(statusRepository.findByName(name));
            }

            if (statuses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(statuses, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new status
    @PostMapping("/status")
    public ResponseEntity<Status> createStatus(@RequestParam  String name) {
        try {

            Status _status = new Status(name);
            statusRepository.save(_status);

            return new ResponseEntity<>(_status, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
