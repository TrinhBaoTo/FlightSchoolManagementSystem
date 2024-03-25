import javax.persistence.*;

@Entity
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "code_iata", nullable = false, length = 3)
    private String codeIata;

    public Airport() {
    }

    public Airport(String codeIata) {
        this.codeIata = codeIata;
    }
}