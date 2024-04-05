package com.example.FlightSchoolManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role_user")
public class RoleUser {

    @EmbeddedId
    @Column(name="id")
    private RoleUserId roleUserId;

    @NonNull
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id", referencedColumnName="id")
    @JsonBackReference
    private User user;

    @NonNull
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", referencedColumnName="id")
    @JsonBackReference
    private Role role;

    public RoleUser(User _user, Role _role) {
        this.roleUserId = new RoleUserId(_user.getId(), _role.getId());
        this.user = _user;
        this.role = _role;
    }
}