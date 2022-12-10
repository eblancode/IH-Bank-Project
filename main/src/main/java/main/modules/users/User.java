package main.modules.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.security.Role;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Column(unique=true)
    private String userName;
    @NotNull
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void addRoleToRoles(Role role) {
        this.roles.add(role);
    }

}
