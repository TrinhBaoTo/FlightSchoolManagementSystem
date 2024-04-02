package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.entity.Certificate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
}

