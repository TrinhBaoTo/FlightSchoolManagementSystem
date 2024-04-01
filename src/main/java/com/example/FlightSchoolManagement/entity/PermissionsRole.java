package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "permissions_role")
public class PermissionsRole{

    @Id
    @GeneratedValue
    @Column(name = "permissions_id")
    private int permissionsId;

    @NonNull
    @Column(name = "role_id", nullable = false)
    private int roleId;
}