package main.modules;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.modules.accounts.Account;
import main.modules.users.AccountHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;
    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private AccountHolder maker;
    private BigDecimal amount;
    private LocalDateTime creationDate = LocalDateTime.now();
    //...

    public Transaction(Account senderAccount, Account receiverAccount, AccountHolder maker, BigDecimal amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.maker = maker;
        this.amount = amount;
    }

}
