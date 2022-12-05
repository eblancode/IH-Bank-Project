package main.modules;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    //id
    // Manytoone
    //senderAcc
    //receiverAcc
    private BigDecimal amount;
    private LocalDate creationDate = LocalDate.now();
    //...
}
