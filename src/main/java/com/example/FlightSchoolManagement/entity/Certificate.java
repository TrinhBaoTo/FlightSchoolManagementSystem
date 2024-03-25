package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "certification_type", nullable = false)
    private String certificationType;

    @Column(name = "certification_expiry_date", nullable = false)
    private Date certificationExpiryDate;

    public Certificate() {
    }

    public Certificate(Date hireDate, String certificationType, Date certificationExpiryDate) {
        this.hireDate = hireDate;
        this.certificationType = certificationType;
        this.certificationExpiryDate = certificationExpiryDate;
    }
}