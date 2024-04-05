package com.example.FlightSchoolManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<RoleUser> roleUser = new HashSet<>();

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @NonNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NonNull
    @JsonIgnore
    @Column(name = "active", nullable = false)
    private int active;

    @NonNull
    @Column(name = "remember_token", nullable = false)
    private String rememberToken;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date  createdAt;

    @NonNull
    @Column(name = "updated_at")
    private Date  updatedAt;

    @NonNull
    @JsonIgnore
    @JoinColumn(name = "certificate_id")
    private int certificateID;
}