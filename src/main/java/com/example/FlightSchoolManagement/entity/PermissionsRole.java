package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions_role")
public class PermissionsRole{

    @Id
    @GeneratedValue
    @Column(name = "permissions_id")
    private int permissionsId;

    @Column(name = "role_id", nullable = false)
    private int roleId;

    public PermissionsRole() {
    }

    public PermissionsRole(int roleId) {
        this.roleId = roleId;
    }
}