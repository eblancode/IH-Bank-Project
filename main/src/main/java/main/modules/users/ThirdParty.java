package main.modules.users;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class ThirdParty extends User {
    private String hashedKey;

    public ThirdParty(String userName, String password, String hashedKey) {
        super(userName, password);
        this.hashedKey = hashedKey;
    }

}
