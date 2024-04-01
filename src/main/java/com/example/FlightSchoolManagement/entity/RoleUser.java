package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "role_user")
public class RoleUser {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @NonNull
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @NonNull
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName="id")
    private Role role;
}