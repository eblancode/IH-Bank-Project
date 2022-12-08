package main.modules.accounts;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.users.AccountHolder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter @Setter @NoArgsConstructor
public class CreditCard extends Account {
    private static final BigDecimal MIN_CREDIT_LIMIT = BigDecimal.valueOf(100);
    private static final BigDecimal MAX_CREDIT_LIMIT = BigDecimal.valueOf(100000);
    private static final double MIN_INTEREST_RATE = 0.1;
    private static final double MAX_INTEREST_RATE = 0.2;

    @DecimalMax("100000")
    @DecimalMin("100")
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
        instantiateCreditLimit(creditLimit);
        // interestRate has default values
    }

    public CreditCard(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
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

    /*@Override
    public void setBalance(BigDecimal balance) {
        super.setBalance(balance);
    }*/

    public void instantiateCreditLimit(BigDecimal creditLimit) {
        if(creditLimit.compareTo(MIN_CREDIT_LIMIT)<0||creditLimit.compareTo(MAX_CREDIT_LIMIT)>0) {
            if(creditLimit.compareTo(MIN_CREDIT_LIMIT)<0) setCreditLimit(MIN_CREDIT_LIMIT);
            else setCreditLimit(MAX_CREDIT_LIMIT);
            System.out.println("Credit Limit for Credit Card got a default value due to constraints");
        }
        else setCreditLimit(creditLimit);
    }

    public void instantiateInterestRate(double interestRate) {
        if (interestRate<MIN_INTEREST_RATE||interestRate>MAX_INTEREST_RATE) {
            if(interestRate<MIN_INTEREST_RATE)
                this.setInterestRate(MIN_INTEREST_RATE); //todo: check if this. necessary?
            else this.setInterestRate(MAX_INTEREST_RATE);
            System.out.println("Interest rate for Credit Card got a default value due to constraints");
        }
        else this.setInterestRate(interestRate);
    }

    protected void checkInterestRate(BigDecimal balance) {
        LocalDate now = LocalDate.now();
        int monthsDifference = Period.between(this.getLastDateInterestRateApplied(),
                now).getMonths();
        if(monthsDifference >= 1) { // CHECK IF OK
            addInterestRateAndSetBalance(balance,monthsDifference,now);
        }
        else {
            this.setBalance(balance);
        }
    }

    private void addInterestRateAndSetBalance(BigDecimal balance, int monthsDifference, LocalDate now) {
        double monthlyInterest = interestRate/12;
        BigDecimal calculatedAmount = balance // CHECK IF OK
                .multiply(BigDecimal.valueOf(monthlyInterest))
                .multiply(BigDecimal.valueOf(monthsDifference));

        this.setLastDateInterestRateApplied(now);
        this.setBalance(balance.add(calculatedAmount));
    }

}
