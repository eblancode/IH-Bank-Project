package main.modules.accounts;

import main.modules.Transaction;
import main.modules.users.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public abstract class Account {
    //id
    private BigDecimal balance;
    //primary
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    //secondary
    //onetomany
    private List<Transaction> inboundTransactionList;
    //onetomany
    private List<Transaction> outboundTransactionList;

}
