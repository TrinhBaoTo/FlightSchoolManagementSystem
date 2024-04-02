package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Certificate;
import com.example.FlightSchoolManagement.entity.RoleUser;
import com.example.FlightSchoolManagement.entity.User;

import com.example.FlightSchoolManagement.repository.CertificateRepository;
import com.example.FlightSchoolManagement.repository.RoleRepository;
import com.example.FlightSchoolManagement.repository.RoleUserRepository;
import com.example.FlightSchoolManagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.Instant;
import java.util.Date;
import java.util.Base64;

import io.jsonwebtoken.Jwts;

import lombok.EqualsAndHashCode;

@RestController
@EqualsAndHashCode
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleUserRepository roleUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CertificateRepository certificateRepository;

    // GET to retrieve all or user by email
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam String email){

        try {
            List<User> users = new ArrayList<User>();

            if (email == null){
                users.addAll(userRepository.findAll());

            } else{
                users.addAll(userRepository.findByEmail(email));
            }

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // POST: add a new user {role} S-student; I-instructor; A-Admin
    // format: SIA;
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                          @RequestParam String password, @RequestParam int phoneNumber, @RequestParam String role,
                          @RequestParam String certificationType, @RequestParam Date certificationExpiryDate) {

        try {

            int active = 1;

            String info = email + role + firstName;
            String rememberToken = Base64.getEncoder().encodeToString(info.getBytes());

            Date  createdAt = Date.from(Instant.now());
            Date  updatedAt = Date.from(Instant.now());

            Certificate _certificate;
            int certId;

            String[] arRole = role.split("-", 0);

            if(Arrays.asList(arRole).contains("I")){
                _certificate = new Certificate( Date.from(Instant.now()),  certificationType,  certificationExpiryDate);
                certId = _certificate.getId();
                certificateRepository.save(_certificate);
            }else{
                certId = -1;
            }

            User _user = new User(firstName, lastName, email, password, phoneNumber, active,
                    rememberToken, createdAt, updatedAt, certId);

            for(String r: arRole){
                RoleUser _roleUser = new RoleUser(_user, roleRepository.findByNameCode(r));
                roleUserRepository.save(_roleUser);
            }

            userRepository.save(_user);

            return new ResponseEntity<>(_user, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT: update an existing user information
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @RequestParam String name) {

            return "User updated successfully";

    }

}
