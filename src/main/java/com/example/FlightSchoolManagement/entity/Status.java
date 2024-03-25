package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 9)
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

}