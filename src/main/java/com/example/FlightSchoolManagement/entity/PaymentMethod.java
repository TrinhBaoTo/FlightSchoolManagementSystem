package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private int name;

    public PaymentMethod() {
    }

    public PaymentMethod(int name) {
        this.name = name;
    }
}