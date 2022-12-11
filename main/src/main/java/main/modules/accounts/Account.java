package main.modules.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import main.modules.Transaction;
import main.modules.users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor @ToString
public abstract class Account {
    private static final BigDecimal PENALTY_FEE = new BigDecimal("40");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    private BigDecimal balance;
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne
    @NotNull
    private AccountHolder primaryOwner;
    @ManyToOne
    private AccountHolder secondaryOwner = null;
    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> inboundTransactionList = new ArrayList<>();
    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> outboundTransactionList = new ArrayList<>();

    public Account(BigDecimal balance, String secretKey, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.status = status;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public Account(BigDecimal balance, String secretKey, Status status, LocalDate creationDate, AccountHolder primaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.status = status;
        this.creationDate = creationDate;
        this.primaryOwner = primaryOwner;
    }

    public BigDecimal checkAndGetBalance() {
        if(this instanceof Savings ||
                this instanceof Checking ||
                this instanceof CreditCard) {
            checkBalance(this.getBalance());
        }
        return this.getBalance();
    }

    public void checkAndSetBalance(BigDecimal balance) {
        // Check Interests/Fees
        if(this instanceof Savings ||
                this instanceof Checking ||
                this instanceof CreditCard) {
            checkBalance(balance);
        }
        else this.setBalance(balance);
    }

    private void checkBalance(BigDecimal balance) {
        String callerMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
        if(this instanceof Savings || this instanceof Checking) {
            if(this instanceof Savings savingsAccount) {
                // Check/Apply penalty fee if balance is due to change
                if(!callerMethod.equals("checkAndGetBalance"))
                    if(savingsAccount.getMinimumBalance().compareTo(balance) > 0){
                        deductPenaltyFeeAndSetBalance(balance);
                    }
                    else this.setBalance(balance);
                // Check/Apply interest rate
                if (savingsAccount.getLastDateInterestRateApplied()!=null) {
                    savingsAccount.checkInterestRate(balance);
                } //todo: check setbalance x2
                return;
            }
            else if (this instanceof Checking checkingAccount) { //todo: Done for Aliases, OK?
                // Check/Apply penalty fee if balance is due to change
                if(!callerMethod.equals("checkAndGetBalance"))
                    if (checkingAccount.getMinimumBalance().compareTo(balance) > 0) {
                        deductPenaltyFeeAndSetBalance(balance);
                    } else this.setBalance(balance);
                return;
            }
        }
        else if (this instanceof CreditCard creditCard) {
            // Check/Apply interest rate
            if (creditCard.getLastDateInterestRateApplied()!=null) {
                creditCard.checkInterestRate(balance);
            }
            else if(callerMethod.equals("checkAndSetBalance")) this.setBalance(balance);
        }
    }

    private void deductPenaltyFeeAndSetBalance(BigDecimal balance) {
        this.setBalance(balance.subtract(PENALTY_FEE));
        System.out.println("A penalty fee was applied to the account."); //todo: check message
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public void addInboundTransactionToList(Transaction inboundTransactionList) {
        this.inboundTransactionList.add(inboundTransactionList);
    }

    public void addOutboundTransactionToList(Transaction outboundTransactionList) {
        this.inboundTransactionList.add(outboundTransactionList);
    }

}
