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
    private static final BigDecimal minMinimumBalance = BigDecimal.valueOf(250);
    private static final BigDecimal minMMF = BigDecimal.valueOf(12);
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

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance.max(minMinimumBalance);
        if(this.minimumBalance.equals(minMinimumBalance))
            System.out.println("Minimum balance for Checking is set to a minimum value");
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee.max(minMMF);
        if(this.monthlyMaintenanceFee.equals(minMMF))
            System.out.println("Monthly maintenance fee for Checking is set to a minimum value");
    }

}
