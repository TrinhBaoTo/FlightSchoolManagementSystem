import javax.persistence.*;

@Entity
@Table(name = "role_user")
public class RoleUser {
    @EmbeddedId
    private RoleUserId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public RoleUser() {
    }

    public RoleUser(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new RoleUserId(user.getId(), role.getId());
    }

}