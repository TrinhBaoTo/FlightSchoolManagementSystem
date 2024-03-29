package com.example.FlightSchoolManagement.controller;

import java.util.*;

import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.entity.Certificate;
import com.example.FlightSchoolManagement.entity.RoleUser;
import com.example.FlightSchoolManagement.entity.User;
import com.example.FlightSchoolManagement.repository.RoleRepository;
import com.example.FlightSchoolManagement.repository.RoleUserRepository;
import com.example.FlightSchoolManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleUserRepository roleUserRepository;

    @Autowired
    RoleRepository roleRepository;


    // GET to retrieve all or user by email
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam String email){

        try {
            List<User> users = new ArrayList<User>();

            if (email == null){
                userRepository.findAll().forEach(users::add);

            } else{
                userRepository.findByEmail(email).forEach(users::add);
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

            String rememberToken = Jwts.builder()
                    .claim("email", email)
                    .claim("role", role)
                    .setSubject(firstName)
                    .setIssuedAt(Date.from(Instant.now()))
                    .compact();

            Date  createdAt = Date.from(Instant.now());
            Date  updatedAt = Date.from(Instant.now());

            Certificate _certificate;

            String[] arRole = role.split("-", 0);

            if(Arrays.asList(arRole).contains("I")){
                _certificate = new Certificate( Date.from(Instant.now()),  certificationType,  certificationExpiryDate);

            }else{
                _certificate = null;
            }

            User _user = new User(firstName, lastName, email, password, phoneNumber, active,
                    rememberToken, createdAt, updatedAt, _certificate);

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

    // PUT: update an existing user
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @RequestParam String name) {

            return "User updated successfully";

    }

    // DELETE: delete a user by ID
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {

            return "User deleted successfully";

    }

    // TRACE: testing purposes (echo server)
    // Failed Test
    @RequestMapping(value = "/trace", method = RequestMethod.TRACE)
    public String echoServer(@RequestBody String body) {
        return body;
    }

    // OPTIONS: get supported HTTP methods
    @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
    public String getSupportedMethods() {
        return "GET, POST, PUT, DELETE, TRACE, OPTIONS, CONNECT, HEAD";
    }

    // HEAD: retrieve only the headers of a resource
    @RequestMapping(value = "/head", method = RequestMethod.HEAD)
    public ResponseEntity<Void> getUserHeaders() {
        // Add new header to return
        HttpHeaders headers = new HttpHeaders();
        headers.add("NEW-header", "xxxxxxx");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // Does not support CONNECT but have PATCH - apply partial modifications to a resource.
}
