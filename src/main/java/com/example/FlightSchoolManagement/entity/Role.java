package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "name_code", nullable = false, length = 1)
    private String nameCode;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Role() {
    }

    public Role(String displayName, String nameCode, String description, Date createdAt, Date updatedAt) {
        this.displayName = displayName;
        this.nameCode = nameCode;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}