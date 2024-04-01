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
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @NonNull
    @Column(name = "certification_type", nullable = false)
    private String certificationType;

    @NonNull
    @Column(name = "certification_expiry_date", nullable = false)
    private Date certificationExpiryDate;
}