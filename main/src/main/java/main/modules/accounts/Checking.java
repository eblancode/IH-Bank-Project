package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor
public class Checking extends Account {
    @Min(250)
    private BigDecimal minimumBalance;
    @Min(12)
    private BigDecimal monthlyMaintenanceFee;

    public Checking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    //TODO: CHECK CONSTRAINTS IN SET METHODS
}
