package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.Certificate;
import com.example.FlightSchoolManagement.entity.RoleUser;
import com.example.FlightSchoolManagement.entity.User;

import com.example.FlightSchoolManagement.repository.CertificateRepository;
import com.example.FlightSchoolManagement.repository.RoleRepository;
import com.example.FlightSchoolManagement.repository.RoleUserRepository;
import com.example.FlightSchoolManagement.repository.UserRepository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.Instant;
import java.util.Date;
import java.util.Base64;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;

@RestController
@EqualsAndHashCode
@Log4j
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
    @JsonIgnore
    public ResponseEntity<List<User>> getUsers(@RequestParam String email){

        try {
            List<User> users = new ArrayList<User>();

            if (email.isEmpty()){
                users = userRepository.findAll();

            } else{
                users.add(userRepository.findByEmail(email));
            }

            if (users.isEmpty()) {

                ResponseEntity<List<User>> r = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                return r;
            }

            ResponseEntity<List<User>> r = new ResponseEntity<>(users, HttpStatus.OK);
            return r;

        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // POST: user sign up
    // {role} S-student; I-instructor; A-Admin
    //        format: SIA;
    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email,
                                          @RequestParam String password, @RequestParam String phoneNumber, @RequestParam String role,
                                          @RequestParam String certificationType, @RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy") Date certificationExpiryDate) {

        try {

            // Set user as active
            int active = 1;

            // Encode token add exp date - 1 year
            String info = email + " " + role +  " "  + firstName;
            String rememberToken = Base64.getEncoder().encodeToString(info.getBytes());

            // Get Date fo Constructor
            Date  createdAt = Date.from(Instant.now());
            Date  updatedAt = Date.from(Instant.now());

            // Initialize Certificate
            Certificate _certificate;
            int certId;

            // Split the role String
            String[] arRole = role.split("-", 0);

            // If role has nameCode = I
            if(Arrays.asList(arRole).contains("I")){

                // Create Certificate
                _certificate = new Certificate( Date.from(Instant.now()),  certificationType,  certificationExpiryDate);
                certificateRepository.save(_certificate);
                certId = _certificate.getId();

            }else{

                // if not set Certificate ID to -1
                certId = -1;
            }

            // Create user and save to inventory
            User _user = new User(firstName, lastName, email, password, phoneNumber, active,
                    rememberToken, createdAt, updatedAt, certId);
            userRepository.save(_user);

            // Set up User and Role connection
            for(String r: arRole){
                RoleUser _roleUser = new RoleUser(_user, roleRepository.findByNameCode(r));
                roleUserRepository.save(_roleUser);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Sign Up Success - " + rememberToken);

        } catch (Exception e) {

            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sign Up Failed");
        }
    }

    // GET: user login
    @GetMapping("/users/login")
    public ResponseEntity<String> loginUser(@RequestParam String token, @RequestParam String email,
                                            @RequestParam String password) {

        // Get user by email
        User _user = userRepository.findByEmail(email);

        // check if password and token para the same with _user
        boolean verified = _user != null && _user.getPassword().equals(password) && _user.getRememberToken().equals(token);

        // if the same
        if(verified){

            // decode token
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedToken = new String(decodedBytes);

            String[] info = decodedToken.split(" ", 0);

            String input = info[0] + " " + info[2];
            String db = email + " " + _user.getFirstName();

            // Check if decoded token information is the same w _user
            if(!input.equals(db)){
                verified = false;
            }
        }

        // ADD SESSION expiration time

        // If user is verified,
        if(verified){

            // Redirect user
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("Location", "https://th.bing.com/th/id/R.4d0233614a2e3260c53e9fde40af2ffb?rik=3EhR%2bPxrPIHYMg&pid=ImgRaw&r=0")
                    .body("Log In Success - redirect to homepage");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Log In Failed");
        }
    }

    // POST: change information
    @PostMapping("/users/update")
    public ResponseEntity<HttpStatus> updateUserInfo(@RequestParam String token, @RequestParam String firstName,
                                                     @RequestParam String lastName, @RequestParam String phoneNumber) {
        try{
            // Assume logged in when reach here
            // Decode token
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedToken = new String(decodedBytes);

            String[] info = decodedToken.split(" ", 0);

            // Get user by email taken from token
            User _user = userRepository.findByEmail(info[0]);

            // Update information
            if(firstName != null){ _user.setFirstName(firstName);}
            if(lastName != null){ _user.setLastName(lastName);}
            if(phoneNumber != null){ _user.setPhoneNumber(phoneNumber);}

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
