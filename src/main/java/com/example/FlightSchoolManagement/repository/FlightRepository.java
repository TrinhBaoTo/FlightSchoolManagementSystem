package com.example.FlightSchoolManagement.repository;

import com.example.FlightSchoolManagement.entity.Airport;
import com.example.FlightSchoolManagement.entity.Flight;
import com.example.FlightSchoolManagement.entity.Lesson;
import com.example.FlightSchoolManagement.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer>  {


    Flight findById(int id);

    List<Flight> findByInstructor(User _user);

    Flight findByFlightNumber(int flightNUmber);

    List<Flight> findByDepartureAirport(@NonNull Airport departureAirport);

    List<Flight> findByStudent(User user);
}
