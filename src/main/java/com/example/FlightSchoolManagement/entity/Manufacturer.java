package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }
}