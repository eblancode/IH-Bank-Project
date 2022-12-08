package main.modules.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import main.modules.Transaction;
import main.modules.users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate // ok?
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor @ToString
public abstract class Account {
    private static final BigDecimal PENALTY_FEE = new BigDecimal("40");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private BigDecimal balance;
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE; // ok?
    /*@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)*/
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne
    //@JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    //@JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;
    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> inboundTransactionList;
    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> outboundTransactionList;

    public Account(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.status = status;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal checkAndGetBalance(BigDecimal balance) {
        if(this instanceof Savings ||
                this instanceof Checking ||
                this instanceof CreditCard) {
            checkBalance(balance);
        }
        return this.getBalance();
    }

    public void checkAndSetBalance(BigDecimal balance) {
        // Check Interests/Fees
        if(this instanceof Savings ||
                this instanceof Checking ||
                this instanceof CreditCard) {
            checkBalance(balance); //todo: EXTRA not needed to check penalty deduction
        }
        else this.setBalance(balance);
    }

    private void checkBalance(BigDecimal balance) {
        if(this instanceof Savings || this instanceof Checking) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

            if(this instanceof Savings savingsAccount) {
                // Check/Apply penalty fee
                if(savingsAccount.getMinimumBalance().compareTo(balance) > 0){
                    deductPenaltyFeeAndSetBalance(balance);
                }
                // Check/Apply interest rate
                if (savingsAccount.getLastDateInterestRateApplied()!=null) {
                    savingsAccount.checkInterestRate(balance);
                }
            }
            else if (this instanceof Checking checkingAccount) { //todo: Done for Aliases, OK?
                // Check/Apply penalty fee
                if(checkingAccount.getMinimumBalance().compareTo(balance) > 0){
                    deductPenaltyFeeAndSetBalance(balance);
                }
            }
        }
        else if (this instanceof CreditCard creditCard) {
            // Check/Apply interest rate
            if (creditCard.getLastDateInterestRateApplied()!=null) {
                creditCard.checkInterestRate(balance);
            }
        }
    }

    private void deductPenaltyFeeAndSetBalance(BigDecimal balance) {
        this.setBalance(balance.subtract(PENALTY_FEE));
    }

}
