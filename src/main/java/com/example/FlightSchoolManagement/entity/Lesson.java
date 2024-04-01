package com.example.FlightSchoolManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

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
    private Long id;

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
    private Time duration;

    @NonNull
    @Column(name = "lesson_daate_time", nullable = false)
    private Date lessonDateTime;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @NonNull
    @Column(name = "notes")
    private String notes;
}