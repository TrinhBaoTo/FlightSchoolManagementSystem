package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Role;
import com.example.FlightSchoolManagement.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    // GET to retrieve all or user by email
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUsers(@RequestParam Integer id){

        try {
            List<Role> roles = new ArrayList<>();

            if (id == -1){
                roles = roleRepository.findAll();

            } else{
                roleRepository.findById(id).ifPresent(roles::add);
            }

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> addUser(@RequestParam String displayName, @RequestParam String nameCode, @RequestParam String description) {

        try {

            Date  createdAt = Date.from(Instant.now());
            Date  updatedAt = Date.from(Instant.now());

            Role _role = new Role( displayName, nameCode, description, createdAt,  updatedAt);

            roleRepository.save(_role);

            return new ResponseEntity<>(_role, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
