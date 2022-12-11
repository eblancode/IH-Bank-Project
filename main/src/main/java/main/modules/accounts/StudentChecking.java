package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor
public class StudentChecking extends Account {
    public StudentChecking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
    }

    public StudentChecking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner) {
        super(balance, secretKey, status, primaryOwner);
    }

}
