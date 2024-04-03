package com.example.FlightSchoolManagement.entity;

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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "role")
    private Set<RoleUser> roleUser = new HashSet<>();

    @NonNull
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @NonNull
    @Column(name = "name_code", nullable = false, length = 1)
    private String nameCode;

    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @NonNull
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @NonNull
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}