package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {

    Aircraft findByRegistrationNumber(int registrationNumber);
}
