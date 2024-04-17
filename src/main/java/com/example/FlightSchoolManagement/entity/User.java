package com.example.FlightSchoolManagement.entity;

import com.fasterxml.jackson.annotation.*;
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
    private int id;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<RoleUser> roleUser = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<LessonUser> lessonUser = new HashSet<>();

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
    @Column(name = "password", nullable = false)
    private String password;

    @NonNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NonNull
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
    @JoinColumn(name = "certificate_id")
    private int certificateID;
}