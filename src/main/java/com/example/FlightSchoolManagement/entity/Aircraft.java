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

    @NonNull
    @Column(name = "registration_number", nullable = false)
    private int registrationNumber;

    @NonNull
    @Column(name = "model", nullable = false)
    private String model;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @NonNull
    @Column(name = "year_manufactured", nullable = false)
    private int yearManufactured;

    @NonNull
    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @NonNull
    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;

    @NonNull
    @Column(name = "availability", nullable = false)
    private int availability;
}