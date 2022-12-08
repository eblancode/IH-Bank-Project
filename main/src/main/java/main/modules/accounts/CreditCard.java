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
import java.time.Period;

@Entity
@Getter @Setter @NoArgsConstructor
public class CreditCard extends Account {
    private static final BigDecimal minCreditLimit = BigDecimal.valueOf(100);
    private static final BigDecimal maxCreditLimit = BigDecimal.valueOf(100000);
    private static final double minInterestRate = 0.1;
    private static final double maxInterestRate = 0.2;

    @Max(100000)
    @Min(100)
    private BigDecimal creditLimit = new BigDecimal("100");
    @DecimalMax("0.2")
    @DecimalMin("0.1")
    private double interestRate = 0.2; //double ok?
    private LocalDate lastDateInterestRateApplied = null;

    //TODO: CHECK CONSTRAINTS IN SET METHODS

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        // creditLimit and interestRate have default values
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
//        this.creditLimit = creditLimit;
        instantiateCreditLimit(creditLimit);
        // interestRate has default values
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
//        this.interestRate = interestRate;
        instantiateInterestRate(interestRate);
        // creditLimit has default values
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        instantiateCreditLimit(creditLimit);
        instantiateInterestRate(interestRate);
        /*this.creditLimit = creditLimit;
        this.interestRate = interestRate;*/
    }

    @Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
    }

    public void instantiateCreditLimit(BigDecimal creditLimit) {
        if(creditLimit.compareTo(minCreditLimit)<0||creditLimit.compareTo(maxCreditLimit)>0) {
            if(creditLimit.compareTo(minCreditLimit)<0) setCreditLimit(minCreditLimit);
            else setCreditLimit(maxCreditLimit);
            System.out.println("Credit Limit for Credit Card got a default value due to constraints");
        }
        else setCreditLimit(creditLimit);
    }

    public void instantiateInterestRate(double interestRate) {
        if (interestRate<minInterestRate||interestRate>maxInterestRate) {
            if(interestRate<minInterestRate) /*this.interestRate = minInterestRate;*/
                setInterestRate(minInterestRate);
            else /*this.interestRate = maxInterestRate;*/setInterestRate(maxInterestRate);
            System.out.println("Interest rate for Credit Card got a default value due to constraints");
        }
        else /*this.interestRate = interestRate;*/setInterestRate(interestRate);
    }

    protected void checkInterestRate(BigDecimal balance) {
        if(Period.between(this.getLastDateInterestRateApplied(),
                LocalDate.now()).getMonths() >= 1) { // CHECK IF OK
            this.addInterestRateAndSetBalance(balance);
        }
        else {
            this.setBalance(balance);
        }
    }

    private void addInterestRateAndSetBalance(BigDecimal balance) {
        BigDecimal calculatedAmount = balance // CHECK IF OK
                .multiply(BigDecimal.valueOf(this.interestRate))
                .multiply(BigDecimal.valueOf(Period.between(this.getLastDateInterestRateApplied(),
                        LocalDate.now()).getMonths()));

        this.setLastDateInterestRateApplied(LocalDate.now());
        this.setBalance(balance.add(calculatedAmount));
    }

}
