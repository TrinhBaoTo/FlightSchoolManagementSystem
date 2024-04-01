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
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "registration_number", nullable = false)
    private int registrationNumber;

    @NonNull
    @Column(name = "model", nullable = false)
    private String model;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "year_manufactured", nullable = false)
    private int yearManufactured;

    @NonNull
    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;

    @Column(name = "availability", nullable = false)
    private int availability;
}