package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "airport")
public class Airport {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "code_iata", nullable = false, length = 3)
    private String codeIata;
}