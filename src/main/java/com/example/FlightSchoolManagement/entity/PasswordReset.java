package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passwords_resets")
public class PasswordReset {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "token", nullable = false)
    private String token;
}