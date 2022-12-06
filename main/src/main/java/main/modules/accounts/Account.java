package main.modules.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.Transaction;
import main.modules.users.AccountHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public abstract class Account {
    private static final BigDecimal PENALTY_FEE = new BigDecimal("40");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private BigDecimal balance;
    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;
    /*@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)*/
    private LocalDate creationDate = LocalDate.now();
    @ManyToOne
//    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
//    @JoinColumn(name = "secondary_owner_id")
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

    protected void checkBalance(BigDecimal balance) {
        //savings necessario?
        if(this instanceof Savings || this instanceof Checking) {
            if(this instanceof Savings savingsAccount) {
                if(savingsAccount.getMinimumBalance().compareTo(balance) < 0){
                    deductPenaltyFeeAndSetBalance(balance);
                }
                // if balance is set or checked, run checkInterestRate
                if (savingsAccount.getLastDateInterestRateApplied()!=null) {
                    savingsAccount.checkInterestRate(balance);
                }
                return;
            }
            else if (this instanceof Checking checkingAccount) { //todo
                /* if (checkingAccount.compareTo(balance) < 0) {
                    deductPenaltyFee(balance);
                }*/
                return;
            }
        }
        if (this instanceof CreditCard) return; //todo

        this.setBalance(balance);
    }

    private void deductPenaltyFeeAndSetBalance(BigDecimal balance) {
        this.setBalance(balance.subtract(PENALTY_FEE));
    }

}
