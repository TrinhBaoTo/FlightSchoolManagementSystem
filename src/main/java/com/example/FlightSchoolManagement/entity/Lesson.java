package com.example.FlightSchoolManagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToMany(mappedBy = "lesson")
    @JsonManagedReference
    private Set<LessonUser> lessonUser = new HashSet<>();

    @NonNull
    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @NonNull
    @Column(name = "duration", nullable = false)
    private Duration duration;

    @NonNull
    @Column(name = "lesson_date_time", nullable = false)
    private Timestamp lessonDateTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @NonNull
    @Column(name = "notes")
    private String notes;
}