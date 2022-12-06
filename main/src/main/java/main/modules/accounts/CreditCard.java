package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class CreditCard extends Account {
    @Max(100000)
    @Min(100)
    private BigDecimal creditLimit = new BigDecimal("100");
    @DecimalMax("0.2")
    @DecimalMin("0.1")
    private double interestRate = 0.2; //double ok?
    private LocalDate lastDateInterestRateApplied = null;

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit; // check it is < 100000 && > 100
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.interestRate = interestRate; // check it is < 0.2 && > 0.1 ?
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    protected void checkInterestRate() {

    }

    protected void addInterestRate() {

    }

}
