package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    Airport findByCodeIata(String codeIata);
}