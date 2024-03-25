import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "name", nullable = false)
    private int name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Role() {
    }

    public Role(String displayName, int name, String description, Date createdAt, Date updatedAt) {
        this.displayName = displayName;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}