package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Permission() {
    }

    public Permission(String name, String displayName, String description, Date createdAt, Date updatedAt) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}