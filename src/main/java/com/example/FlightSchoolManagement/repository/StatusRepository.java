package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Manufacturer;
import com.example.FlightSchoolManagement.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByName(String name);
}
