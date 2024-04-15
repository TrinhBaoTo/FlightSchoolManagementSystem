package com.example.FlightSchoolManagement.controller;

import com.example.FlightSchoolManagement.entity.PaymentMethod;
import com.example.FlightSchoolManagement.repository.PaymentMethodRepository;

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
public class PaymentMethodController {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;


    // GET to retrieve all methods
    @GetMapping("/methods")
    public ResponseEntity<List<PaymentMethod>> getAllMethod(@RequestParam(required = false) String name) {
        try {

            List<PaymentMethod> methods = new ArrayList<>();

            if (name != null){

                methods = paymentMethodRepository.findAll();

            } else{
                methods.add(paymentMethodRepository.findByName(name));
            }

            if (methods.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(methods, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST: add a new status
    @PostMapping("/method")
    public ResponseEntity<PaymentMethod> createMethod(@RequestParam  String name) {
        try {

            PaymentMethod _method = new PaymentMethod(name);
            paymentMethodRepository.save(_method);

            return new ResponseEntity<>(_method, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
