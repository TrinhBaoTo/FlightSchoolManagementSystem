import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "flight_number", nullable = false)
    private int flightNumber;

    @ManyToOne
    @JoinColumn(name = "departure_airport", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport", nullable = false)
    private Airport arrivalAirport;

    @Column(name = "departure_date_time", nullable = false)
    private Date departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private Date arrivalDateTime;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    public Flight() {
    }

    public Flight(int flightNumber, Airport departureAirport, Airport arrivalAirport, Date departureDateTime,
                  Date arrivalDateTime, Aircraft aircraft, User instructor, User student) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.aircraft = aircraft;
        this.instructor = instructor;
        this.student = student;
    }

}