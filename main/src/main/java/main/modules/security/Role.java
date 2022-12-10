package main.modules.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.User;

@Entity
@Getter @Setter @NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String role;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role(String role) {
        this.role = role;
    }

    public Role(String role, User user) {
        this.role = role;
        this.user = user;
    }

}
