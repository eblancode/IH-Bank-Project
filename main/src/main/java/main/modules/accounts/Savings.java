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
public class Savings extends Account {
    private static final BigDecimal MIN_MINIMUM_BALANCE = BigDecimal.valueOf(100);
    private static final BigDecimal MAX_MINIMUM_BALANCE = BigDecimal.valueOf(1000);
    private static final double MAX_INTEREST_RATE = 0.5;

    @DecimalMin("100")
    @DecimalMax("1000")
    private BigDecimal minimumBalance = new BigDecimal("1000");
    @DecimalMax("0.5")
    private double interestRate = 0.0025; //double ok?
    private LocalDate lastDateInterestRateApplied = null;

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        // minimumBalance and interestRate have default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner) {
        super(balance, secretKey, status, primaryOwner);
        // minimumBalance and interestRate have default values and there is no secondary owner
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        instantiateMinimumBalance(minimumBalance);
        // interestRate has default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        instantiateInterestRate(interestRate);
        // minimumBalance has default values
    }

    public Savings(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal minimumBalance, double interestRate) {
        super(balance, secretKey, status, primaryOwner, secondaryOwner);
        instantiateMinimumBalance(minimumBalance);
        instantiateInterestRate(interestRate);
    }

    /*Called in constructor, checks if the value is in between minimum and maximum allowed values.
    If the value provided is not in the range then sets it to the minimum/maximum value accordingly*/
    public void instantiateMinimumBalance(BigDecimal minimumBalance) {
        if(minimumBalance.compareTo(MIN_MINIMUM_BALANCE)<0||minimumBalance.compareTo(MAX_MINIMUM_BALANCE)>0) {
            if(minimumBalance.compareTo(MIN_MINIMUM_BALANCE)<0) setMinimumBalance(MIN_MINIMUM_BALANCE);
            else setMinimumBalance(MAX_MINIMUM_BALANCE);
            System.out.println("Minimum balance for Savings got a default value due to constraints");
        }
        else setMinimumBalance(minimumBalance);
    }

    /*Called in constructor, checks if the value is lower than the maximum allowed.
    If the value provided is not lower, then it sets to the maximum value*/
    public void instantiateInterestRate(double interestRate) {
        this.interestRate = Math.min(interestRate, MAX_INTEREST_RATE);
        if(this.interestRate==MAX_INTEREST_RATE)
            System.out.println("Interest rate for Savings is set to a maximum value");
    }

    // Method to check interest rate and apply if proceeds
    protected void checkInterestRate(BigDecimal balance) {
        LocalDate now = LocalDate.now();
        int yearsDifference = Period.between(this.getLastDateInterestRateApplied(),
                now).getYears();
        if(yearsDifference >= 1) { // CHECK IF OK
            addInterestRateAndSetBalance(balance,yearsDifference,now);
        }
        else {
            setBalance(balance);
        }
    }

    // Apply interest rate
    private void addInterestRateAndSetBalance(BigDecimal balance, int yearsDifference, LocalDate now) {
        BigDecimal calculatedAmount = balance // CHECK IF OK
                .multiply(BigDecimal.valueOf(this.interestRate))
                .multiply(BigDecimal.valueOf(yearsDifference));

        setLastDateInterestRateApplied(now);
        setBalance(balance.add(calculatedAmount));
    }

}
