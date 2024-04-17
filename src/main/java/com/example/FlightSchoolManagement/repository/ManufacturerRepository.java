package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    Manufacturer findByName(String name);
}
