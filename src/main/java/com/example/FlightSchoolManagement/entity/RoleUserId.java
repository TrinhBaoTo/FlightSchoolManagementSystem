package com.example.FlightSchoolManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RoleUserId implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;
}