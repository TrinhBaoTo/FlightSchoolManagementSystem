package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.controller.PaymentMethodController;
import com.example.FlightSchoolManagement.entity.Manufacturer;
import com.example.FlightSchoolManagement.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    PaymentMethod findByName(String name);
}
