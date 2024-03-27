package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passwords_resets")
public class PasswordReset {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "token", nullable = false)
    private String token;

    public PasswordReset() {
    }

    public PasswordReset(int userId, String token) {
        this.userId = userId;
        this.token = token;


    }
}