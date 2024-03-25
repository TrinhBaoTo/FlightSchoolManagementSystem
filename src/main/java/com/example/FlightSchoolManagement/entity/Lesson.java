import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "lesson_name", nullable = false)
    private String lessonName;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Column(name = "duration", nullable = false)
    private Time duration;

    @Column(name = "lesson_daate_time", nullable = false)
    private Date lessonDateTime;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    private Status status;

    @Column(name = "notes")
    private String notes;

    public Lesson() {
    }

    public Lesson(String lessonName, User instructor, Aircraft aircraft, Time duration, Date lessonDateTime,
                  Status status, String notes) {
        this.lessonName = lessonName;
        this.instructor = instructor;
        this.aircraft = aircraft;
        this.duration = duration;
        this.lessonDateTime = lessonDateTime;
        this.status = status;
        this.notes = notes;
    }
}