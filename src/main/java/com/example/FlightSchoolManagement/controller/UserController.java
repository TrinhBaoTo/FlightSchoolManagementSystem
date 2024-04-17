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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private JavaMailSender mailSender;

    private String verfCode = "";

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

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);

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

            String rememberToken = "";

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

            // Notes - Good Practice
            //  Password should be encode

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

    // GET: user sign in
    @GetMapping("/users/signin")
    public ResponseEntity<String> signInUser(@RequestParam String email, @RequestParam String password) {

        // Get user by email
        User _user = userRepository.findByEmail(email);

        // check if password and token para the same with _user
        boolean verified = _user != null && _user.getPassword().equals(password);

        // If user is verified,
        if(verified){

            String rememberToken = tokenGenerator(_user);

            // Notes - Good Practice
            //   SHOULD NOT have password in token

            // Redirect user
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .body("Sign In Success - " + rememberToken);

        }else{

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Sign In Failed");
        }
    }

    // GET: user sign out
    @GetMapping("/users/signout")
    public ResponseEntity<String> signOutUser(@RequestHeader("Authorization") String bearerToken) {

        try {

            String token = extractToken(bearerToken);

            User _user = getUser(token);

            if(checkUser(token)){

                token = "";

                _user.setRememberToken(token);
                userRepository.save(_user);

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Sign Out Success");
            }else{

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Sign Out Failed");
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Sign Out Failed");
        }
    }

    // POST: change information
    @PostMapping("/users/update")
    public ResponseEntity<String> updateUserInfo(@RequestHeader("Authorization") String bearerToken, @RequestParam String firstName,
                                                     @RequestParam String lastName, @RequestParam String phoneNumber) {
        try{

            String token = extractToken(bearerToken);

            // Get user by email taken from token
            User _user = getUser(token);

            String update = "Update: ";

            Date  updatedAt = Date.from(Instant.now());

            // Authenticate the user
            if(_user != null && checkUser(token)){

                // Notes: change updatedAt
                // Update information
                if(!firstName.isEmpty()){
                    _user.setFirstName(firstName);
                    _user.setUpdatedAt(updatedAt);
                    userRepository.save(_user);
                    update = update + "firstName ";
                }
                if(!lastName.isEmpty()){
                    _user.setLastName(lastName);
                    _user.setUpdatedAt(updatedAt);
                    userRepository.save(_user);
                    update = update + "lastname ";
                }
                if(!phoneNumber.isEmpty()){
                    _user.setPhoneNumber(phoneNumber);
                    _user.setUpdatedAt(updatedAt);
                    userRepository.save(_user);
                    update = update + "phoneNumber ";
                }

                return new ResponseEntity<>(update, HttpStatus.OK);

            }else{

                return new ResponseEntity<>("User Not Found/ Not Verified", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/users/resetpassword")
    public ResponseEntity<String> resetPassword( @RequestParam String email) {

        try{

            // Get user by email
            User _user = userRepository.findByEmail(email);

            // Send the user
            if(_user != null){

                verfCode = getRandomNumberString();

                String from = "zimsby@gmail.com";
                String to = "zimsby@gmail.com";

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(from);
                message.setTo(to);
                message.setSubject("This is a plain text email");
                message.setText(verfCode);

                mailSender.send(message);

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Send Verification Code to User Email - " + verfCode);

            }else{

                return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/checkverification")
    public ResponseEntity<String> checkPasswordResetVerification( @RequestParam String email, @RequestParam String newPassword, @RequestParam String verificationCode) {

        try{

            // Get user by email
            User _user = userRepository.findByEmail(email);

            // Send the user
            if(_user != null && verificationCode.equals(verfCode)){

                Date  updatedAt = Date.from(Instant.now());

                _user.setPassword(newPassword);
                _user.setUpdatedAt(updatedAt);
                userRepository.save(_user);

                tokenGenerator(_user);

                verfCode = "";

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Password Reset Successfully");

            }else{

                return new ResponseEntity<>("Failed to Verified", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkUser(String token) {

        User _user = getUser(token);

        // check if password and token para the same with _user
        boolean verified = _user != null && _user.getRememberToken().equals(token);

        // if the same
        if(verified){

            // decode token
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedToken = new String(decodedBytes);

            String[] info = decodedToken.split(" ", 0);

            String input = info[0] + " " + info[2];
            String db = _user.getEmail() + " " + _user.getPassword();

            // Check if decoded token information is the same w _user
            if(!input.equals(db)){
                verified = false;
            }

            // Check Token Expiration Date
            LocalDateTime expDate = LocalDateTime.parse(info[3]);

            if(expDate.isBefore(LocalDateTime.now())){

                // tokenGenerator(_user);
                verified = false;
            }
        }

        return verified;
    }

    public boolean checkRole(String r, User _user){

        // Get User roles
        List<RoleUser> roleusers = roleUserRepository.findByUser(_user);

        for( RoleUser ru : roleusers){

            if(ru.getRole().getNameCode().equals(r)){
                return true;
            }
        }

        return false;
    }

    public User getUser(String token){

        // decode token
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedToken = new String(decodedBytes);

        String[] info = decodedToken.split(" ", 0);

        User _user = userRepository.findByEmail(info[0]);

        return _user;
    }

    private String tokenGenerator(User _user){

        // Get User roles
        List<RoleUser> roleusers = roleUserRepository.findByUser(_user);

        String rol = "";

        for( RoleUser ru : roleusers){

            rol = rol + ru.getRole().getNameCode();
        }

        // Expiration Date
        // Notes: add time (Date Time)
        String expDate = LocalDateTime.parse(LocalDateTime.now().toString()).plusDays(7).toString();

        // Encode token
        String info = _user.getEmail() + " "  + rol +  " "  + _user.getPassword() + " " + expDate;

        String rememberToken = Base64.getEncoder().encodeToString(info.getBytes());
        _user.setRememberToken(rememberToken);
        userRepository.save(_user);

        return rememberToken;
    }

    public static String getRandomNumberString() {
        // Generate a 6-digit random number from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // Convert the number to a 6-character string by padding with leading zeros
        return String.format("%06d", number);
    }

    // Notes: Type "/**" + enter to get the comment section under
    /**
     * This function extract a bearer token
     *
     * @param bearerToken
     * @return String
     */
    private String extractToken(String bearerToken) {

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            // Remove "Bearer " prefix
            return bearerToken.substring(7);
        }

        return null;
    }
}
