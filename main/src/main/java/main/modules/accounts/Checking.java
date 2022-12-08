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
    /*private static final BigDecimal minMinimumBalance = BigDecimal.valueOf(250);
    private static final BigDecimal minMMF = BigDecimal.valueOf(12);*/
    @DecimalMin("250") //todo: do it static final? (needed in the db?)
    @DecimalMax("250")
    private final BigDecimal minimumBalance = BigDecimal.valueOf(250);
    @DecimalMin("12")
    @DecimalMax("12")
    private final BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12);

    public Checking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
    }

    /*public Checking(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }*/

    //TODO: TEST CONSTRAINTS IN SET METHODS, OK?

    /*public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance.max(minMinimumBalance);
        if(this.minimumBalance.equals(minMinimumBalance))
            System.out.println("Minimum balance for Checking is set to a minimum value");
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee.max(minMMF);
        if(this.monthlyMaintenanceFee.equals(minMMF))
            System.out.println("Monthly maintenance fee for Checking is set to a minimum value");
    }*/

}
