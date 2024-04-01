package com.example.FlightSchoolManagement.repository;

import java.util.List;

import com.example.FlightSchoolManagement.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findByCodeIata(String codeIata);
}