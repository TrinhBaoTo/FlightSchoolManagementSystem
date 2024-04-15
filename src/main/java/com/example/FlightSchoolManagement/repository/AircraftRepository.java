package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

    Aircraft findByRegistrationNumber(int registrationNumber);
}
