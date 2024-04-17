package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    PaymentMethod findByName(String name);
}
