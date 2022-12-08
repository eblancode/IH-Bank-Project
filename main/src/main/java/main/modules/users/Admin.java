package main.modules.users;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Admin extends User {
    public Admin(String userName) {
        super(userName);
    }

}
