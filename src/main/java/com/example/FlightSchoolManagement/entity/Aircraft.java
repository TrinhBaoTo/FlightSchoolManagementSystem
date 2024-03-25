package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "registration_number", nullable = false)
    private int registrationNumber;

    @Column(name = "model", nullable = false)
    private String model;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "year_manufactured", nullable = false)
    private int yearManufactured;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "purchase_price", nullable = false)
    private double purchasePrice;

    @Column(name = "availability", nullable = false)
    private int availability;

    public Aircraft() {
    }

    public Aircraft(int registrationNumber, String model, Manufacturer manufacturer, int yearManufactured,
                    Date purchaseDate, double purchasePrice, int availability) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.yearManufactured = yearManufactured;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.availability = availability;
    }
}