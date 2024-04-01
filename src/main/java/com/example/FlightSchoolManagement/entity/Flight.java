package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "flight_number", nullable = false)
    private int flightNumber;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "departure_airport", nullable = false)
    private Airport departureAirport;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "arrival_airport", nullable = false)
    private Airport arrivalAirport;

    @NonNull
    @Column(name = "departure_date_time", nullable = false)
    private Date departureDateTime;

    @NonNull
    @Column(name = "arrival_date_time", nullable = false)
    private Date arrivalDateTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
}