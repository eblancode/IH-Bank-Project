package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor
public class Checking extends Account {
    @DecimalMin("250")
    @DecimalMax("250")
    private final BigDecimal minimumBalance = BigDecimal.valueOf(250);
    @DecimalMin("12")
    @DecimalMax("12")
    private final BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);

    public Checking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
    }

}
