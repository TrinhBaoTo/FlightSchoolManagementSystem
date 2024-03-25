package com.example.FlightSchoolManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.jpa.postgresql.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByCode(String codeIata);
}