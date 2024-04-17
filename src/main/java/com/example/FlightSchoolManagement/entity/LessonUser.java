package com.example.FlightSchoolManagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
        import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lesson_user")
public class LessonUser {

    // For Student User
    @EmbeddedId
    @Column(name="id")
    private LessonUserId lessonUserId;

    @NonNull
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name="user_id", referencedColumnName="id")
    @JsonBackReference
    private User user;

    @NonNull
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "lesson_id", referencedColumnName="id")
    @JsonBackReference
    private Lesson lesson;

    public LessonUser(User _user, Lesson _lesson) {
        this.lessonUserId = new LessonUserId(_user.getId(), _lesson.getId());
        this.user = _user;
        this.lesson = _lesson;
    }
}