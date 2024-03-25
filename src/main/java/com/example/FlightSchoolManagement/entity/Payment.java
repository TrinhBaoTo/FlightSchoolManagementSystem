import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    public Payment() {
    }

    public Payment(User student, int amount, Date paymentDate, PaymentMethod paymentMethod) {
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
}