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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "amount", nullable = false)
    private int amount;

    @NonNull
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
}