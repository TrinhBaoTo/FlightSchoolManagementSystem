package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role_user")
public class RoleUser {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public RoleUser() {
    }

    public RoleUser(User user, Role role) {
        this.user = user;
        this.role = role;
    }

}