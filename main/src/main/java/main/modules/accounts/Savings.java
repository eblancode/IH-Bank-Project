package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter @Setter @NoArgsConstructor
public class Savings extends Account {
    @Max(1000) // Aplicar validacion en dto y en setter de misma clase
    @Min(100)
    private BigDecimal minimumBalance = new BigDecimal("1000");
    @DecimalMax("0.5")
    private double interestRate = 0.0025; //double ok?
    private LocalDate lastDateInterestRateApplied = null;

    //TODO: CHECK CONSTRAINTS IN SET METHODS

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        // minimumBalance and interestRate have default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.minimumBalance = minimumBalance;
        // interestRate has default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        // minimumBalance has default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate; //check it is < 0.5 ?
    }

    protected void checkInterestRate(BigDecimal balance) {
        if(Period.between(this.getLastDateInterestRateApplied(),
                LocalDate.now()).getYears() >= 1) { // CHECK IF OK
            this.addInterestRateAndSetBalance(balance);
        }
        else {
            this.setBalance(balance);
        }
    }

    protected void addInterestRateAndSetBalance(BigDecimal balance) {
        BigDecimal calculatedAmount = balance // CHECK IF OK
                .multiply(BigDecimal.valueOf(this.interestRate))
                .multiply(BigDecimal.valueOf(Period.between(this.getLastDateInterestRateApplied(),
                        LocalDate.now()).getYears()));

        this.setLastDateInterestRateApplied(LocalDate.now());
        this.setBalance(balance.add(calculatedAmount));
    }

}
