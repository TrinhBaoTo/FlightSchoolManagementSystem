package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;
}